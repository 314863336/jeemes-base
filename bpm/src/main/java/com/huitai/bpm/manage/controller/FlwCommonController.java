package com.huitai.bpm.manage.controller;

import com.huitai.bpm.manage.entity.*;
import com.huitai.bpm.manage.service.FlwCommonService;
import com.huitai.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 公共流程请求处理层 <br>
 * @date 2020-12-02 17:04 <br>
 */
@RestController
@RequestMapping("/flw/manage/common")
public class FlwCommonController {

    @Autowired
    private FlwCommonService flwCommonService;

    @PostMapping("task/execute")
    public Result executeTask(FlwBaseEntity flwBaseEntity, @RequestBody Map<String, Object> params) {
        return Result.ok(flwCommonService.executeTask(flwBaseEntity, params));
    }

    @PutMapping("task/claim")
    public Result claimTask(FlwBaseEntity flwBaseEntity, @RequestBody FlwTask flwTask) {
        flwCommonService.claimTask(flwBaseEntity, flwTask.getId(), flwTask.getLoginCode(), true);
        return Result.ok();
    }

    @PutMapping("task/unclaim")
    public Result unclaimTask(FlwBaseEntity flwBaseEntity) {
        flwCommonService.unclaimTask(flwBaseEntity);
        return Result.ok();
    }

    @PutMapping("task/delegate")
    public Result delegateTask(FlwBaseEntity flwBaseEntity, String loginCode) {
        flwCommonService.delegateTask(flwBaseEntity, loginCode);
        return Result.ok();
    }

    @PutMapping("task/reslove")
    public Result resloveTask(FlwBaseEntity flwBaseEntity, @RequestBody Map<String, Object> params) {
        flwCommonService.resloveTask(flwBaseEntity, params);
        return Result.ok();
    }

    @PutMapping("task/transfer")
    public Result transferTask(FlwBaseEntity flwBaseEntity, String loginCode) {
        flwCommonService.transferTask(flwBaseEntity, loginCode);
        return Result.ok();
    }

    @GetMapping("task/buttons")
    public Result buttons(FlwBaseEntity flwBaseEntity) {
        return Result.ok(flwCommonService.buttons(flwBaseEntity));
    }

    @GetMapping("task/comments")
    public Result comments(FlwBaseEntity flwBaseEntity) {
        return Result.ok(flwCommonService.comments(flwBaseEntity));
    }

    @GetMapping("task/before-nodes")
    public Result beforeNodes(FlwBaseEntity flwBaseEntity) {
        return Result.ok(flwCommonService.findBeforeNodes(flwBaseEntity));
    }

    @PutMapping("task/go-back")
    public Result goBack(FlwBaseEntity flwBaseEntity, @RequestBody FlwFlowNode flwFlowNode) {
        return Result.ok(flwCommonService.goBack(flwBaseEntity, flwFlowNode, new HashMap<>()));
    }

    @GetMapping("task/current-task")
    public Result currentTask(FlwBaseEntity flwBaseEntity) {
        return Result.ok(flwCommonService.currentTask(flwBaseEntity));
    }

    @PutMapping("process/termination")
    public Result termination(FlwBaseEntity flwBaseEntity) {
        flwCommonService.termination(flwBaseEntity);
        return Result.ok();
    }

    @GetMapping("process/todo/page")
    public Result todoPage(FlwPage<FlwTodoEntity, List<FlwTodoEntity>> flwPage) {
        return Result.ok(flwCommonService.todoPage(flwPage));
    }

    @PutMapping("task/claim/batch")
    public Result claimBatchTask(@RequestBody List<FlwTask> list, Boolean isHistoric) {
        flwCommonService.claimBatchTask(list, isHistoric);
        return Result.ok();
    }

    @PostMapping("check/is-same-node")
    public Result isSameNode(@RequestBody List<FlwBaseEntity> list) {
        return Result.ok(flwCommonService.isSameNode(list));
    }
}
