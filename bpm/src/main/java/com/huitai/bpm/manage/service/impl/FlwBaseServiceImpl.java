package com.huitai.bpm.manage.service.impl;

import com.huitai.bpm.examples.dao.FlwBaseMapper;
import com.huitai.bpm.exception.FlwManageCommonException;
import com.huitai.bpm.manage.entity.FlwBaseEntity;
import com.huitai.bpm.manage.service.FlwBaseService;
import com.huitai.bpm.manage.service.FlwCommonService;
import com.huitai.bpm.utils.CommonUtils;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.utils.UserUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 工作流基础服务层接口实现 <br>
 * @date 2020-12-02 17:26 <br>
 */
public abstract class FlwBaseServiceImpl<M extends FlwBaseMapper<T>, T extends FlwBaseEntity> extends BaseServiceImpl<M, T> implements FlwBaseService<T> {

    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    private FlwCommonService flwCommonService;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> startProcess(T flwBaseEntity, Map<String, Object> variables) {

        String processDefinitionKey = flwBaseEntity.getClass().getCanonicalName();
        String simpleServiceName = CommonUtils.lowerFirstChar(this.getClass().getSimpleName());
        String businessKey = simpleServiceName + BUSINESS_KEY_SEPARATOR + flwBaseEntity.getId();
        if (StringUtil.isNotBlank(flwBaseEntity.getBusinessType()))
            variables.put(BUSINESS_TYPE, flwBaseEntity.getBusinessType());
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
            Map<String, Object> map = new HashMap<>();
            return this.updateBusiness(flwBaseEntity, processInstance, map);
        } catch (NullValueException e) {

            String[] args = {Thread.currentThread().getStackTrace()[1].getFileName(), String.valueOf(Thread.currentThread().getStackTrace()[1].getLineNumber())};
            String message = messageSource.getMessage("bpm.manage.error.startKey.notFound", args, LocaleContextHolder.getLocale());

            throw new FlwManageCommonException(message);
        }
    }

    /**
     * @description: 更新业务实体 <br>
     * @param: flwBaseEntity 业务对象 <br>
     * @param: flwBaseEntity 流程实例对象 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-11 14:05 <br>
     */
    @Transactional(readOnly = false)
    public Map<String, Object> updateBusiness(T flwBaseEntity, ProcessInstance processInstance, Map<String, Object> map) {
        String loginCode = Objects.requireNonNull(UserUtil.getCurUser()).getLoginCode();
        flwBaseEntity = this.baseMapper.selectById(flwBaseEntity.getId());
        flwBaseEntity.setProcessInstanceId(processInstance.getProcessInstanceId());
        flwBaseEntity.setUpdateBy(loginCode);
        flwBaseEntity.setUpdateDate(new Date());
        flwBaseEntity.setStatus(FLW_STATUS_PROCESS_STARTED);
        flwCommonService.findAssignees(flwBaseEntity, map);
        String assignee = (String) map.get(FlwCommonService.EXECUTE_RETURN_MAP_TYPE_ASSIGNEE);
        if (StringUtil.isNotBlank(assignee)) {
            flwBaseEntity.setOwners(ASSIGNEES_SEPARATOR + assignee + ASSIGNEES_SEPARATOR);
            flwBaseEntity.setAssignees(ASSIGNEES_SEPARATOR + assignee + ASSIGNEES_SEPARATOR);
        }
        this.baseMapper.updateById(flwBaseEntity);
        map.put(ENTITY, flwBaseEntity);
        return map;
    }
}
