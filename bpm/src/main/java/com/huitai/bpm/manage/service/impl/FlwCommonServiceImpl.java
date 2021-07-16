package com.huitai.bpm.manage.service.impl;

import com.huitai.bpm.exception.FlwManageCommonException;
import com.huitai.bpm.manage.entity.*;
import com.huitai.bpm.manage.enumerate.FlwEnum;
import com.huitai.bpm.manage.service.FlwBaseService;
import com.huitai.bpm.manage.service.FlwCommonService;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotEmpty;
import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 公共流程服务接口实现 <br>
 * @date 2020-12-02 17:06 <br>
 */
@Service
@Transactional(readOnly = true)
public class FlwCommonServiceImpl implements FlwCommonService {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> executeTask(FlwBaseEntity flwBaseEntity, Map<String, Object> variables) {
        Task task = getCurrentUserTask(flwBaseEntity);
        if (StringUtil.isNotBlank(flwBaseEntity.getComment()))
            taskService.createComment(task.getId(), task.getProcessInstanceId(), COMMENT_PREFIX_SUBMIT + flwBaseEntity.getComment());
        taskService.complete(task.getId(), variables);
        Map<String, Object> map = updateBusiness(flwBaseEntity, null, null);
        map.put(VARIABLES, variables);
        return map;
    }

    /**
     * @description: 更新业务实例 <br>
     * @param: flwBaseEntity 业务实体 向上转型对象 <br>
     * @param: assignee 指定参与人，null时默认获取下一个办理人 <br>
     * @return: status 指定流程状态，null时默认程序自行获取 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-07 11:52 <br>
     */
    private Map<String, Object> updateBusiness(FlwBaseEntity flwBaseEntity, String assignee, String status) {
        Map<String, Object> map = new HashMap<>();
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(flwBaseEntity.getProcessInstanceId());
        if (StringUtil.isBlank(status)) {
            status = getStatus(flwBaseEntity, false);
        }
        map.put(EXECUTE_RETURN_MAP_TYPE_STATUS, status);
        String businessKey = historicProcessInstance.getBusinessKey();
        String[] split = businessKey.split(BUSINESSKEY_SEPARATOR);
        Object bean = SpringContextUtil.getBean(split[0]);
        if (bean instanceof FlwBaseService) {
            FlwBaseService flwBaseService = (FlwBaseService) bean;
            Object entity = flwBaseService.getById(split[1]);
            if (entity instanceof FlwBaseEntity) {
                boolean isEnd = false;
                flwBaseEntity = (FlwBaseEntity) entity;
                flwBaseEntity.setUpdateBy(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode());
                flwBaseEntity.setUpdateDate(new Date());
                flwBaseEntity.setStatus(status);
                if (!(FlwBaseService.FLW_STATUS_PROCESS_FINISH.equals(status) || FlwBaseService.FLW_STATUS_PROCESS_TERMINATION.equals(status))) {
                    if (StringUtil.isBlank(assignee)) {
                        isEnd = findAssignees(flwBaseEntity, map);
                        if (!isEnd)
                            assignee = (String) map.get(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE);
                    }
                    if (!isEnd) {
                        if (StringUtil.isNotBlank(assignee)) {
                            flwBaseEntity.setOwners(FlwBaseService.ASSIGNEES_SEPARATOR + assignee + FlwBaseService.ASSIGNEES_SEPARATOR);
                            if (StringUtil.isNotBlank(flwBaseEntity.getAssignees())) {
                                flwBaseEntity.setAssignees(flwBaseEntity.getAssignees() + assignee + FlwBaseService.ASSIGNEES_SEPARATOR);
                            } else {
                                flwBaseEntity.setAssignees(FlwBaseService.ASSIGNEES_SEPARATOR + assignee + FlwBaseService.ASSIGNEES_SEPARATOR);
                            }
                        } else {
                            return map; // 如果是 弹窗 直接交给前端处理去
                        }
                    }
                }
                if (isEnd || FlwBaseService.FLW_STATUS_PROCESS_FINISH.equals(status) || FlwBaseService.FLW_STATUS_PROCESS_TERMINATION.equals(status)) {
                    flwBaseEntity.setOwners(STRING_BLANK);
                }
                if (!isEnd && FlwBaseService.FLW_STATUS_PROCESS_TERMINATION.equals(status)) {
                    List<Task> list = taskService
                            .createTaskQuery()
                            .processInstanceId(flwBaseEntity.getProcessInstanceId())
                            .active()
                            .list();
                    if (CollectionUtils.isNotEmpty(list)) {
                        StringBuilder owners = new StringBuilder(FlwBaseService.ASSIGNEES_SEPARATOR);
                        for (Task task : list) {
                            owners.append(task.getAssignee()).append(FlwBaseService.ASSIGNEES_SEPARATOR);
                        }
                        flwBaseEntity.setOwners(owners.toString());
                    }
                }
                flwBaseService.updateById(flwBaseEntity);
            } else {
                String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber()), FlwBaseEntity.class.getSimpleName()};
                String message = messageSource.getMessage("bpm.manage.error.not.inherited", args, LocaleContextHolder.getLocale());
                throw new FlwManageCommonException(message);
            }
        } else {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber()), FlwBaseService.class.getSimpleName()};
            String message = messageSource.getMessage("bpm.manage.error.not.inherited", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }

        return map;
    }

    @Override
    public boolean findAssignees(FlwBaseEntity flwBaseEntity, Map<String, Object> map) {

        List<Task> list = taskService
                .createTaskQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .active()
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return true;
        }

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .singleResult();
        BpmnModelInstance bpmnModelInstance = repositoryService
                .getBpmnModelInstance(processInstance.getProcessDefinitionId());

        List<FlwTask> usersList = new ArrayList<>();
        List<FlwTask> groupsList = new ArrayList<>();

        list.forEach(task -> {

            if (StringUtil.isNotBlank(task.getAssignee())) {
                String assignee = (String) map.get(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE);
                if (StringUtil.isBlank(assignee)) {
                    map.put(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE, task.getAssignee());
                } else {
                    map.put(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE, assignee + FlwBaseService.ASSIGNEES_SEPARATOR + task.getAssignee());
                }
            } else {
                ModelElementInstance modelElementInstance = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());

                if (modelElementInstance instanceof UserTask) {
                    UserTask userTask = (UserTask) modelElementInstance;

                    String camundaAssignee = userTask.getCamundaAssignee();

                    if (StringUtil.isNotBlank(camundaAssignee) && camundaAssignee.startsWith(CONVERSION_SIGN_START)) {
                        camundaAssignee = conversion(camundaAssignee, task);
                    }

                    if (StringUtil.isNotBlank(camundaAssignee)) {
                        String assignee = (String) map.get(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE);
                        if (StringUtil.isBlank(assignee)) {
                            map.put(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE, camundaAssignee);
                        } else {
                            map.put(EXECUTE_RETURN_MAP_TYPE_ASSIGNEE, assignee + FlwBaseService.ASSIGNEES_SEPARATOR + camundaAssignee);
                        }
                    } else {
                        String camundaCandidateUsers = userTask.getCamundaCandidateUsers();
                        if (StringUtil.isBlank(camundaCandidateUsers)) {
                            String camundaCandidateGroups = userTask.getCamundaCandidateGroups();
                            if (StringUtil.isNotBlank(camundaCandidateGroups)) {
                                groupsList.add(new FlwTask(task.getId(), task.getProcessInstanceId(), task.getTaskDefinitionKey(), task.getName(), task.getAssignee(), task.getCreateTime(), null, camundaCandidateGroups));
                            } else {
                                String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
                                String message = messageSource.getMessage("bpm.manage.error.not.assignee", args, LocaleContextHolder.getLocale());
                                throw new FlwManageCommonException(message);
                            }
                        } else {
                            usersList.add(new FlwTask(task.getId(), task.getProcessInstanceId(), task.getTaskDefinitionKey(), task.getName(), task.getAssignee(), task.getCreateTime(), camundaCandidateUsers, null));
                        }
                    }
                }
            }
        });

        if (CollectionUtils.isNotEmpty(usersList)) {
            map.put(EXECUTE_RETURN_MAP_TYPE_USERS_LIST, usersList);
        }

        if (CollectionUtils.isNotEmpty(groupsList)) {
            map.put(EXECUTE_RETURN_MAP_TYPE_GROUPS_LIST, groupsList);
        }

        return false;
    }

    @Override
    public FlwPage<FlwTodoEntity, List<FlwTodoEntity>> todoPage(FlwPage<FlwTodoEntity, List<FlwTodoEntity>> flwPage) {
        List<FlwTodoEntity> flwTodoEntityList = null;

        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode());
        long count = taskQuery.count();
        flwPage.setTotal(count);
        int maxResults = new Long(flwPage.getSize()).intValue();
        int firstResult = ((new Long(flwPage.getCurrent()).intValue()) - 1) * maxResults;
        List<Task> taskList = taskQuery
                .orderByTaskCreateTime().desc()
                .listPage(firstResult, maxResults);

        if (CollectionUtils.isNotEmpty(taskList)) {
            flwTodoEntityList = taskList.stream().map(task -> {
                FlwTodoEntity flwTodoEntity = new FlwTodoEntity();
                //flwDeployment
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
                        .createProcessDefinitionQuery()
                        .processDefinitionId(task.getProcessDefinitionId())
                        .singleResult();
                flwTodoEntity.setFlwDeployment(new FlwDeployment(processDefinition.getDeploymentId(),
                        processDefinition.getName(),
                        processDefinition.getKey(),
                        processDefinition.getCategory(),
                        processDefinition.getVersion(),
                        processDefinition.getSuspensionState()));
                //createBy
                FlwBaseEntity flwBaseEntity = getFlwBaseEntity(task.getProcessInstanceId());
                String createBy = flwBaseEntity.getCreateBy();
                if (StringUtil.isNotBlank(createBy)) {
                    flwTodoEntity.setCreateBy(createBy);
                }
                //flwTask
                flwTodoEntity.setFlwTask(new FlwTask(task.getId(), task.getName(), task.getAssignee(), task.getCreateTime()));
                //startTime
                HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(task.getProcessInstanceId());
                String startActivityId = historicProcessInstance.getStartActivityId();
                ensureNotNull(START_ACTIVITY_ID, startActivityId);
                List<HistoricActivityInstance> historicActivityInstances = historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId())
                        .activityId(startActivityId)
                        .orderPartiallyByOccurrence()
                        .asc()
                        .list();
                ensureNotEmpty(HISTORIC_ACTIVITY_INSTANCES, historicActivityInstances);
                HistoricActivityInstance startActivityInstance = historicActivityInstances.get(0);
                flwTodoEntity.setStartTime(startActivityInstance.getStartTime());

                return flwTodoEntity;
            }).collect(Collectors.toList());
        }

        flwPage.setList(flwTodoEntityList);

        return flwPage;
    }

    @Override
    @Transactional(readOnly = false)
    public void claimBatchTask(List<FlwTask> list, Boolean isHistoric) {
        if (CollectionUtils.isNotEmpty(list)) {
            if (isHistoric == null || !isHistoric) {
                list.forEach(flwTask -> {
                    claimTask(new FlwBaseEntity(flwTask.getProcessInstanceId()), flwTask.getId(), flwTask.getLoginCode(), false);
                });
            } else {
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService
                        .createHistoricActivityInstanceQuery()
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().desc();
                list.forEach(flwTask -> {
                    List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery
                            .processInstanceId(flwTask.getProcessInstanceId())
                            .activityId(flwTask.getDefinitionKey())
                            .list();
                    HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(0);
                    String assignee = historicActivityInstance.getAssignee();
                    flwTask.setLoginCode(assignee);
                    claimTask(new FlwBaseEntity(flwTask.getProcessInstanceId()), flwTask.getId(), flwTask.getLoginCode(), false);
                });
            }

            FlwTask flwTask = list.get(0);
            StringBuilder owners = new StringBuilder();
            List<Task> taskList = taskService
                    .createTaskQuery()
                    .processInstanceId(flwTask.getProcessInstanceId())
                    .active()
                    .list();
            for (Task task : taskList) {
                String assignee = task.getAssignee();
                owners.append(assignee).append(FlwBaseService.ASSIGNEES_SEPARATOR);
            }
            owners.deleteCharAt(owners.lastIndexOf(FlwBaseService.ASSIGNEES_SEPARATOR));
            updateBusiness(new FlwBaseEntity(flwTask.getProcessInstanceId()), owners.toString(), null);
        }
    }

    @Override
    public boolean isSameNode(List<FlwBaseEntity> list) {
        boolean result = true;
        String currentTaskDefinitionKey = null;
        for (FlwBaseEntity flwBaseEntity : list) {
            Task task = taskService
                    .createTaskQuery()
                    .processInstanceId(flwBaseEntity.getProcessInstanceId())
                    .active()
                    .taskAssignee(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode())
                    .singleResult();
            if (StringUtil.isNotBlank(currentTaskDefinitionKey)) {
                if (!currentTaskDefinitionKey.equals(task.getTaskDefinitionKey())) {
                    result = false;
                    break;
                }
            }
            currentTaskDefinitionKey = task.getTaskDefinitionKey();
        }
        return result;
    }

    /**
     * @description: 获取业务对象 <br>
     * @param: processInstanceId 流程实例主键 <br>
     * @return: FlwBaseEntity 业务数据对象的向上转型对象 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-22 11:24 <br>
     */
    private FlwBaseEntity getFlwBaseEntity(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(processInstanceId);
        String businessKey = historicProcessInstance.getBusinessKey();
        String[] split = businessKey.split(BUSINESSKEY_SEPARATOR);
        Object bean = SpringContextUtil.getBean(split[0]);
        if (bean == null) {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber()), FlwBaseService.class.getSimpleName(), businessKey};
            String message = messageSource.getMessage("bpm.manage.error.business.key.null", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }
        if (bean instanceof FlwBaseService) {
            FlwBaseService flwBaseService = (FlwBaseService) bean;
            Object entity = flwBaseService.getById(split[1]);
            if (entity instanceof FlwBaseEntity) {
                return (FlwBaseEntity) entity;
            } else {
                /*String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber()), FlwBaseEntity.class.getSimpleName()};
                String message = messageSource.getMessage("bpm.manage.error.not.inherited", args, LocaleContextHolder.getLocale());
                throw new FlwManageCommonException(message);*/
                return null;
            }
        } else {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber()), FlwBaseService.class.getSimpleName()};
            String message = messageSource.getMessage("bpm.manage.error.not.inherited", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }
    }

    /**
     * @description: 转换变量 <br>
     * @param: camundaAssignee 源数据 <br>
     * @param: task 任务实例 <br>
     * @return: String 转换后的数据 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-08 10:42 <br>
     */
    private String conversion(String camundaAssignee, Task task) {
        camundaAssignee = camundaAssignee.substring(camundaAssignee.indexOf(CONVERSION_SIGN_START) + 2, camundaAssignee.indexOf(CONVERSION_SIGN_END));
        return (String) taskService.getVariable(task.getId(), camundaAssignee);
    }

    @Override
    @Transactional(readOnly = false)
    public void claimTask(FlwBaseEntity flwBaseEntity, String taskId, String loginCode, boolean isUpdateBusiness) {
        if (StringUtil.isBlank(taskId)) {
            Task task = taskService
                    .createTaskQuery()
                    .processInstanceId(flwBaseEntity.getProcessInstanceId())
                    .active()
                    .singleResult();
            taskId = task.getId();
        }
        if (StringUtil.isBlank(loginCode)) {
            loginCode = Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode();
        }
        taskService.claim(taskId, loginCode);
        if (isUpdateBusiness) {
            updateBusiness(flwBaseEntity, loginCode, null);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void unclaimTask(FlwBaseEntity flwBaseEntity) {
        Task task = getCurrentUserTask(flwBaseEntity);
        taskService.claim(task.getId(), null);
    }

    private Task getCurrentUserTask(FlwBaseEntity flwBaseEntity) {
        return getCurrentUserTask(flwBaseEntity, true);
    }

    @Override
    public Task getCurrentUserTask(FlwBaseEntity flwBaseEntity, boolean isCheck) {
        if (flwBaseEntity == null || StringUtil.isBlank(flwBaseEntity.getProcessInstanceId())) {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.manage.error.get.task.miss", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }
        Task task = taskService
                .createTaskQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .active()
                .taskAssignee(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode())
                .singleResult();
        if (isCheck && task == null) {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.manage.error.get.task.notFound", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }
        return task;
    }

    @Override
    @Transactional(readOnly = false)
    public void delegateTask(FlwBaseEntity flwBaseEntity, String loginCode) {
        Task task = getCurrentUserTask(flwBaseEntity);
        taskService.delegateTask(task.getId(), loginCode);
    }

    @Override
    public void resloveTask(FlwBaseEntity flwBaseEntity, Map<String, Object> variables) {
        Task task = getCurrentUserTask(flwBaseEntity);
        taskService.resolveTask(task.getId(), variables);
    }

    @Override
    @Transactional(readOnly = false)
    public void transferTask(FlwBaseEntity flwBaseEntity, String loginCode) {
        Task task = getCurrentUserTask(flwBaseEntity);
        taskService.setAssignee(task.getId(), loginCode);
        taskService.createComment(task.getId(), flwBaseEntity.getProcessInstanceId(), COMMENT_PREFIX_TRANSFER + flwBaseEntity.getComment());
        updateBusiness(flwBaseEntity, null, null);
    }

    @Override
    public List<FlwEnum> buttons(FlwBaseEntity flwBaseEntity) {
        List<FlwEnum> list = new ArrayList<>();
        Task task = taskService.createTaskQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .active()
                .taskAssignee(Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode())
                .singleResult();
        if (task != null) {
            list.add(FlwEnum.BUTTON_SUBMIT);
            list.add(FlwEnum.BUTTON_TRANSFER);
            list.add(FlwEnum.BUTTON_TERMINATION);
            List<FlwFlowNode> beforeNodes = findBeforeNodes(flwBaseEntity);
            if (CollectionUtils.isNotEmpty(beforeNodes))
                list.add(FlwEnum.BUTTON_GO_BACK);
        }
        return list;
    }

    @Override
    public List<FlwComment> comments(FlwBaseEntity flwBaseEntity) {
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments(flwBaseEntity.getProcessInstanceId());
        List<FlwComment> collect = processInstanceComments.stream().map(comment -> {
            HistoricTaskInstance historicTaskInstance = historyService
                    .createHistoricTaskInstanceQuery()
                    .taskId(comment.getTaskId())
                    .singleResult();
            return new FlwComment(historicTaskInstance.getName(),
                    historicTaskInstance.getStartTime(),
                    historicTaskInstance.getEndTime(),
                    computeConsumeTime(historicTaskInstance.getStartTime(), historicTaskInstance.getEndTime()),
                    historicTaskInstance.getAssignee(),
                    comment.getFullMessage());
        }).collect(Collectors.toList());

        List<HistoricActivityInstance> finishedList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        finishedList.forEach(historicActivityInstance -> {
            String activityType = historicActivityInstance.getActivityType();
            if (activityType.toUpperCase().contains(START_EVENT)) {
                collect.add(new FlwComment(historicActivityInstance.getActivityName(),
                        historicActivityInstance.getStartTime(),
                        historicActivityInstance.getEndTime(),
                        computeConsumeTime(historicActivityInstance.getStartTime(), historicActivityInstance.getEndTime()),
                        historicActivityInstance.getAssignee(),
                        START_MESSAGE));
            }
            if (activityType.toUpperCase().contains(END_EVENT)) {
                collect.add(0, new FlwComment(historicActivityInstance.getActivityName(),
                        historicActivityInstance.getStartTime(),
                        historicActivityInstance.getEndTime(),
                        computeConsumeTime(historicActivityInstance.getStartTime(), historicActivityInstance.getEndTime()),
                        historicActivityInstance.getAssignee(),
                        END_MESSAGE));
            }
        });

        return collect;
    }

    @Override
    public List<FlwFlowNode> findBeforeNodes(FlwBaseEntity flwBaseEntity) {
        List<FlowNode> list = new ArrayList<>();
        Task task = getCurrentUserTask(flwBaseEntity, false);
        if (task != null) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(flwBaseEntity.getProcessInstanceId()).singleResult();
            BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processInstance.getProcessDefinitionId());
            handle(list, (FlowNode) bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey()));
            return list.stream().map(flowNode -> new FlwFlowNode(flowNode.getId(), flowNode.getName())).collect(Collectors.toList());

        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> goBack(FlwBaseEntity flwBaseEntity, FlwFlowNode flwFlowNode, Map<String, Object> variables) {
        ActivityInstance tree = runtimeService.getActivityInstance(flwBaseEntity.getProcessInstanceId());
        Task task = getCurrentUserTask(flwBaseEntity);
        runtimeService.createProcessInstanceModification(flwBaseEntity.getProcessInstanceId())
                .cancelActivityInstance(getInstanceIdForActivity(tree, task.getTaskDefinitionKey()))
                .setAnnotation(flwBaseEntity.getComment())
                .startBeforeActivity(flwFlowNode.getId())
                .setVariables(variables)
                .execute();
        taskService.createComment(task.getId(), flwBaseEntity.getProcessInstanceId(), COMMENT_PREFIX_GO_BACK + flwBaseEntity.getComment());
        Map<String, Object> map = updateBusiness(flwBaseEntity, null, null);
        map.put(VARIABLES, variables);
        return map;
    }

    @Override
    public FlwTask currentTask(FlwBaseEntity flwBaseEntity) {
        Task currentUserTask = getCurrentUserTask(flwBaseEntity, false);
        if (currentUserTask != null) {
            return new FlwTask(currentUserTask.getId(), currentUserTask.getName(), currentUserTask.getAssignee(), currentUserTask.getCreateTime());
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void termination(FlwBaseEntity flwBaseEntity) {
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .singleResult();
        BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(historicProcessInstance.getProcessDefinitionId());
        Collection<EndEvent> modelElementsByType = bpmn.getModelElementsByType(EndEvent.class);
        if (CollectionUtils.isNotEmpty(modelElementsByType) || modelElementsByType.size() == 1) {
            List<EndEvent> collect = new ArrayList<>(modelElementsByType);
            EndEvent endEvent = collect.get(0);
            ActivityInstance tree = runtimeService.getActivityInstance(flwBaseEntity.getProcessInstanceId());
            Task task = getCurrentUserTask(flwBaseEntity);
            runtimeService.createProcessInstanceModification(flwBaseEntity.getProcessInstanceId())
                    .cancelActivityInstance(getInstanceIdForActivity(tree, task.getTaskDefinitionKey()))
                    .setAnnotation(flwBaseEntity.getComment())
                    .startBeforeActivity(endEvent.getId())
                    .execute();
            taskService.createComment(task.getId(), flwBaseEntity.getProcessInstanceId(), COMMENT_PREFIX_TERMINATION + flwBaseEntity.getComment());
            updateBusiness(flwBaseEntity, null, FlwBaseService.FLW_STATUS_PROCESS_TERMINATION);
        } else {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.manage.error.endings", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        }
    }

    /**
     * @description: 获取流程状态 <br>
     * @param: processInstanceId 流程实例编号 <br>
     * @param: isCheck 是否检测合法 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-07 11:15 <br>
     */
    private String getStatus(FlwBaseEntity flwBaseEntity, boolean isCheck) {

        if (isCheck)
            getHistoricProcessInstance(flwBaseEntity.getProcessInstanceId());

        List<Execution> list = runtimeService
                .createExecutionQuery()
                .processInstanceId(flwBaseEntity.getProcessInstanceId())
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return FlwBaseService.FLW_STATUS_PROCESS_FINISH;
        } else {
            return FlwBaseService.FLW_STATUS_PROCESS_STARTED;
        }

    }

    /**
     * @description: 获取历史流程实例 <br>
     * @param: processInstanceId 流程实例编号 <br>
     * @return: HistoricProcessInstance 历史流程实例 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-07 11:49 <br>
     */
    private HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (Objects.isNull(historicProcessInstance)) {
            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.manage.error.processInstance.null", args, LocaleContextHolder.getLocale());
            throw new FlwManageCommonException(message);
        } else {
            return historicProcessInstance;
        }
    }

    private String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId) {
        ActivityInstance instance = getChildInstanceForActivity(activityInstance, activityId);
        if (instance != null) {
            return instance.getId();
        }
        return null;
    }

    private ActivityInstance getChildInstanceForActivity(ActivityInstance activityInstance, String activityId) {
        if (activityId.equals(activityInstance.getActivityId())) {
            return activityInstance;
        }
        for (ActivityInstance childInstance : activityInstance.getChildActivityInstances()) {
            ActivityInstance instance = getChildInstanceForActivity(childInstance, activityId);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    /**
     * @description: 收集指定节点所有可以退回的节点 <br>
     * @param: <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-05 9:12 <br>
     */
    private void handle(List list, FlowNode flowNode) {
        Collection<SequenceFlow> incoming = flowNode.getIncoming();
        incoming.forEach(sequenceFlow -> {
            FlowNode source = sequenceFlow.getSource();
            if (source instanceof ParallelGateway || source instanceof InclusiveGateway || source instanceof StartEvent) {
                return;
            } else {
                list.add(source);
                handle(list, source);
            }
        });
    }

    /**
     * @description: 计算开始和结束时间差 <br>
     * @param: startTime 开始时间 <br>
     * @param: endTime 结束时间 <br>
     * @return: String 时间相差结果 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-17 18:23 <br>
     */
    private String computeConsumeTime(Date startTime, Date endTime) {

        StringBuilder stringBuilder = new StringBuilder();

        long nd = 1000 * 60 * 60 * 24, nh = 1000 * 60 * 60, nm = 1000 * 60, ns = 1000, diff = 0;

        try {
            diff = endTime.getTime() - startTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long day = diff / nd;
        if (day != 0)
            stringBuilder.append(day).append("天");

        long hour = diff % nd / nh;
        if (hour != 0)
            stringBuilder.append(hour).append("小时");

        long min = diff % nd % nh / nm;
        if (min != 0)
            stringBuilder.append(min).append("分钟");

        long sec = diff % nd % nh % nm / ns;
        if (sec != 0)
            stringBuilder.append(sec).append("秒");

        return stringBuilder.toString();
    }
}
