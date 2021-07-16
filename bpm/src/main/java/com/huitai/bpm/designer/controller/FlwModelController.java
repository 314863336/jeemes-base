package com.huitai.bpm.designer.controller;

import com.huitai.bpm.designer.service.FlwModelService;
import com.huitai.common.model.Result;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 工作流模型请求处理层 <br>
 * @date 2020-11-27 9:57 <br>
 */
@RestController
@RequestMapping("/flw/designer/model")
public class FlwModelController {

    @Autowired
    private FlwModelService flwModelService;

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("find/process/keys")
    public Result findProcessKeys() {
        return Result.ok(flwModelService.findProcessKeys());
    }

    @GetMapping("/diagram/svg/no-frills/{id}")
    public Result noFrills(@PathVariable String id) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(id)
                .singleResult();
        return Result.ok(flwModelService.noFrillsSvg(processDefinition.getId()));
    }

    @GetMapping("/diagram/svg/frills/{id}")
    public Result frills(@PathVariable String id) {
        return Result.ok(flwModelService.frillsSvg(id));
    }

}
