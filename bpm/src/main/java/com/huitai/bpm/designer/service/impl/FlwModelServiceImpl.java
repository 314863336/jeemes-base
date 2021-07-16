package com.huitai.bpm.designer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.bpm.annotaion.FlwAnnotaion;
import com.huitai.bpm.designer.entity.PopupEntity;
import com.huitai.bpm.designer.service.FlwModelService;
import com.huitai.bpm.exception.FlwDesignerCommonException;
import com.huitai.bpm.manage.entity.FlwBaseEntity;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysUserService;
import com.huitai.core.utils.UserUtil;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 工作流模型服务层接口实现 <br>
 * @date 2020-11-27 10:41 <br>
 */
@Service
public class FlwModelServiceImpl implements FlwModelService {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private HtSysUserService htSysUserService;

    @Override
    public Map<String, String> findProcessKeys() {
        Map<String, String> resultMap = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(REFLECTIONS_FORPACKAGES));
        Set<Class<? extends FlwBaseEntity>> set = reflections.getSubTypesOf(FlwBaseEntity.class);
        set.forEach(entity -> {
            boolean annotationPresent = entity.isAnnotationPresent(FlwAnnotaion.class);
            if (annotationPresent) {
                FlwAnnotaion annotation = entity.getAnnotation(FlwAnnotaion.class);
                String canonicalName = entity.getCanonicalName();
                resultMap.put(canonicalName, annotation.value());
            }
        });
        return resultMap;
    }

    @Override
    public String noFrillsSvg(String processDefinitionId) {

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        String resourceName = processDefinition.getResourceName();

        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);

        byte[] bytes = new byte[0];
        try {
            bytes = toByteArray(resourceAsStream);
        } catch (IOException e) {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.designer.error.to.bytes", args, LocaleContextHolder.getLocale());
            throw new FlwDesignerCommonException(message);
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public Map<String, Object> frillsSvg(String processInstanceId) {

        Map<String, Object> resultMap = new HashMap<>();

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (historicProcessInstance != null) {
            String noFrillsSvg = noFrillsSvg(historicProcessInstance.getProcessDefinitionId());
            resultMap.put(FRILLSSVG_BPMNXML, noFrillsSvg);
            resultMap.putAll(getHighlightNode(processInstanceId));
        }

        return resultMap;
    }

    /**
     * @description: 获取高亮节点 <br>
     * @param: processInstanceId 流程实例主键 <br>
     * @return: map 高亮数据集 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-15 10:52 <br>
     */
    private Map<String, Object> getHighlightNode(String processInstanceId) {

        Map<String, Object> resultMap = new HashMap<>();

        Set<String> highPoint = new HashSet<>();
        Set<String> waitingToDo = new HashSet<>();
        Set<String> iDo = new HashSet<>();
        Set<String> highLine2 = new HashSet<>();
        Map<String, PopupEntity> nodeMap = new HashMap<>();

        List<HistoricActivityInstance> unfinished = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .unfinished()
                .list();

        unfinished.forEach(historicActivityInstance -> waitingToDo.add(historicActivityInstance.getActivityId()));

        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode())
                .finished()
                .processInstanceId(processInstanceId)
                .list();
        taskInstanceList.forEach(historicTaskInstance -> iDo.add(historicTaskInstance.getTaskDefinitionKey()));

        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(historicProcessInstance.getProcessDefinitionId());

        Collection<UserTask> userTaskCollection = bpmn.getModelElementsByType(UserTask.class);
        userTaskCollection.forEach(userTask -> {
            nodeMap.put(userTask.getId(), new PopupEntity(userTask.getId(), userTask.getName(), getUserName(userTask.getCamundaAssignee()), dateFormatToStr(null), dateFormatToStr(null)));
        });

        List<HistoricActivityInstance> finishedList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
        finishedList.forEach(historicActivityInstance1 -> {
            nodeMap.put(historicActivityInstance1.getActivityId(), new PopupEntity(historicActivityInstance1.getActivityId(), historicActivityInstance1.getActivityName(), getUserName(historicActivityInstance1.getAssignee()), dateFormatToStr(historicActivityInstance1.getStartTime()), dateFormatToStr(historicActivityInstance1.getEndTime())));
            highPoint.add(historicActivityInstance1.getActivityId());
            ModelElementInstance modelElementInstance = bpmn.getModelElementById(historicActivityInstance1.getActivityId());
            //转换成flowNode流程节点，获取到输出线和输入线
            FlowNode flowNode = (FlowNode) modelElementInstance;
            Collection<SequenceFlow> outgoing = flowNode.getOutgoing();
            outgoing.forEach(sequenceFlow -> {
                String targetId = sequenceFlow.getTarget().getId();
                finishedList.forEach(historicActivityInstance2 -> {
                    String finxId = historicActivityInstance2.getActivityId();
                    if (targetId.equals(finxId)) {
                        if (historicActivityInstance1.getEndTime().equals(historicActivityInstance2.getStartTime())) {
                            highLine2.add(sequenceFlow.getId());
                        }
                    }
                });

                unfinished.forEach(historicActivityInstance3 -> {
                    nodeMap.put(historicActivityInstance3.getActivityId(), new PopupEntity(historicActivityInstance3.getActivityId(), historicActivityInstance3.getActivityName(), getUserName(historicActivityInstance3.getAssignee()), dateFormatToStr(historicActivityInstance3.getStartTime()), dateFormatToStr(historicActivityInstance3.getEndTime())));
                    String finxId = historicActivityInstance3.getActivityId();
                    if (targetId.equals(finxId)) {
                        if (historicActivityInstance1.getEndTime().equals(historicActivityInstance3.getStartTime())) {
                            highLine2.add(sequenceFlow.getId());
                        }
                    }
                });

            });
        });

        resultMap.put(FRILLSSVG_HIGHPOINT, highPoint);
        resultMap.put(FRILLSSVG_HIGHLINE, highLine2);
        resultMap.put(FRILLSSVG_WAITINGTODO, waitingToDo);
        resultMap.put(FRILLSSVG_IDO, iDo);
        resultMap.put(FRILLSSVG_NODEMAP, nodeMap);

        return resultMap;
    }

    /**
     * @description: 根据登录账号获取用户名 <br>
     * @param: loginCode 登录账号 <br>
     * @return: String 用户名称 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-15 14:22 <br>
     */
    private String getUserName(String loginCode) {
        String assignee = "";
        QueryWrapper<HtSysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(LOGIN_CODE, loginCode);
        HtSysUser htSysUser = htSysUserService.getBaseMapper().selectOne(queryWrapper);
        if (htSysUser != null) {
            assignee = htSysUser.getUserName();
        }
        return assignee;
    }

    /**
     * @description: 日期格式化 <br>
     * @param: date 日期对象 <br>
     * @return: 格式化后的字符串日期表示 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-15 14:08 <br>
     */
    private String dateFormatToStr(Date date) {
        if (date != null) {
            String strDateFormat = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * @description: 获取输入字节流中的字节 <br>
     * @param: <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-08 10:56 <br>
     */
    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
