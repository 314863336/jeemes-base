package com.huitai.bpm.manage.service;

import com.huitai.bpm.manage.entity.FlwBaseEntity;
import com.huitai.core.base.BaseIService;

import java.util.Map;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: FlwBaseService <br>
 * @date 2020-12-02 17:20 <br>
 */
public interface FlwBaseService<T extends FlwBaseEntity> extends BaseIService<T> {

    String FLW_STATUS_PROCESS_STARTED = "1";
    String FLW_STATUS_PROCESS_FINISH = "2";
    String FLW_STATUS_PROCESS_TERMINATION = "3";
    String ASSIGNEES_SEPARATOR = ",";
    String BUSINESS_KEY_SEPARATOR = ".";
    String BUSINESS_TYPE = "bt";
    String ENTITY = "entity";

    /**
     * @description: 启动流程 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: variables 流程变量 <br>
     * @return: Map<String, Object> 包含状态： 流程状态，1 审批中 2 流程结束；包含办理人或候选人组信息 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 17:17 <br>
     */
    Map<String, Object> startProcess(T flwBaseEntity, Map<String, Object> variables);
}
