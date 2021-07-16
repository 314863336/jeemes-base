package com.huitai.bpm.manage.service;

import com.huitai.bpm.manage.entity.*;
import com.huitai.bpm.manage.enumerate.FlwEnum;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 公共流程业务层接口 <br>
 * @date 2020-12-02 17:05 <br>
 */
public interface FlwCommonService {

    String START_EVENT = "STARTEVENT";

    String END_EVENT = "ENDEVENT";

    String START_MESSAGE = "流程启动";

    String END_MESSAGE = "流程结束";

    String COMMENT_PREFIX_SUBMIT = FlwEnum.BUTTON_SUBMIT.getLabel() + "@";

    String COMMENT_PREFIX_GO_BACK = FlwEnum.BUTTON_GO_BACK.getLabel() + "@";

    String COMMENT_PREFIX_TERMINATION = FlwEnum.BUTTON_TERMINATION.getLabel() + "@";

    String COMMENT_PREFIX_TRANSFER = FlwEnum.BUTTON_TRANSFER.getLabel() + "@";

    String BUSINESSKEY_SEPARATOR = "\\.";

    String CONVERSION_SIGN_START = "${";

    String CONVERSION_SIGN_END = "}";

    String EXECUTE_RETURN_MAP_TYPE_STATUS = "status";

    String EXECUTE_RETURN_MAP_TYPE_ASSIGNEE = "assignee";

    String EXECUTE_RETURN_MAP_TYPE_USERS_LIST = "usersList";

    String EXECUTE_RETURN_MAP_TYPE_GROUPS_LIST = "groupsList";

    String VARIABLES = "variables";

    String START_ACTIVITY_ID = "startActivityId";

    String HISTORIC_ACTIVITY_INSTANCES = "historicActivityInstances";

    String STRING_BLANK = "";

    /**
     * @description: 执行任务 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: variables 流程变量 <br>
     * @return: Map<String, Object> 包含状态： 流程状态，1 审批中 2 流程结束；包含办理人或候选人组信息 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 11:57 <br>
     */
    Map<String, Object> executeTask(FlwBaseEntity flwBaseEntity, Map<String, Object> variables);

    /**
     * @description: 任务签收（不指定loginCode时，默认当前登录人） <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: taskId 任务主键，为null时自动获取当前用户在当前流程实例下的任务（不适用并行多任务情况下） <br>
     * @param: loginCode 签收用户账号 <br>
     * @param: isUpdateBusiness 是否需要更新业务数据 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 15:37 <br>
     */
    void claimTask(FlwBaseEntity flwBaseEntity, String taskId, String loginCode, boolean isUpdateBusiness);

    /**
     * @description: 任务反签收 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 15:51 <br>
     */
    void unclaimTask(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 获取当前用户在指定流程下的任务 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: isCheck 是否检测合法 <br>
     * @return: Task 任务实体 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 16:37 <br>
     */
    Task getCurrentUserTask(FlwBaseEntity flwBaseEntity, boolean isCheck);

    /**
     * @description: 任务委派：将任务委派给指定用户 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: loginCode 被委派的用户账号<br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 16:07 <br>
     */
    void delegateTask(FlwBaseEntity flwBaseEntity, String loginCode);

    /**
     * @description: 任务归还：被委派人完成任务之后，将任务归还委派人 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: variables 流程变量 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 18:25 <br>
     */
    void resloveTask(FlwBaseEntity flwBaseEntity, Map<String, Object> variables);

    /**
     * @description: 任务转办：任务转让，无须返还给转办人 <br>
     * @param: flwBaseEntity 业务对象 多态接收 <br>
     * @param: loginCode 转办的目标账号<br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-03 18:38 <br>
     */
    void transferTask(FlwBaseEntity flwBaseEntity, String loginCode);

    /**
     * @description: 获取当前用户可操作的按钮列表 <br>
     * @param: flwBaseEntity 业务对象 <br>
     * @return: List 按钮列表 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-16 15:35 <br>
     */
    List<FlwEnum> buttons(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 获取指定业务实例的审批意见 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @return: List 审批意见 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-17 16:19 <br>
     */
    List<FlwComment> comments(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 查找能够退回的节点 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @return: List<FlwFlowNode> 可退回的节点 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-18 16:11 <br>
     */
    List<FlwFlowNode> findBeforeNodes(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 回退 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @param: flwFlowNode 目标节点 <br>
     * @return: Map<String, Object> 岗位受理人情况下包含办理人或候选人组信息 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-05 13:38 <br>
     */
    Map<String, Object> goBack(FlwBaseEntity flwBaseEntity, FlwFlowNode flwFlowNode, Map<String, Object> variables);

    /**
     * @description: 获取当前用户办理的任务 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @return: FlwTask 任务对象 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-14 18:26 <br>
     */
    FlwTask currentTask(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 终止当前整个流程 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-20 14:02 <br>
     */
    void termination(FlwBaseEntity flwBaseEntity);

    /**
     * @description: 根据流程实例编号查找下一个任务办理人 <br>
     * @param: flwBaseEntity 业务实例 <br>
     * @param: map 查询结果存储 <br>
     * @return: boolean false 流程未结束，true 流程已结束 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-07 14:32 <br>
     */
    boolean findAssignees(FlwBaseEntity flwBaseEntity, Map<String, Object> map);

    /**
     * @description: 获取当前登录人待办列表 <br>
     * @param: flwPage 工作流分页实体，包含分页查询参数 <br>
     * @return: flwPage 工作流分页实体，包含List数据集 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-22 9:57 <br>
     */
    FlwPage<FlwTodoEntity, List<FlwTodoEntity>> todoPage(FlwPage<FlwTodoEntity, List<FlwTodoEntity>> flwPage);

    /**
     * @description: 批量签收 <br>
     * @param: list 签收任务数据集 <br>
     * @param: isHistoric 专为回退扩充参数，true时任务自动交由上一次办理人处理 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-27 13:46 <br>
     */
    void claimBatchTask(List<FlwTask> list, Boolean isHistoric);

    /**
     * @description: 判断一批流程业务数据是否是同一个环节 <br>
     * @param: list 流程业务数据 <br>
     * @return: <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-02-03 13:46 <br>
     */
    boolean isSameNode(List<FlwBaseEntity> list);
}
