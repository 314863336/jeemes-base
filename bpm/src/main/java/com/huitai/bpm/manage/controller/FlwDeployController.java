package com.huitai.bpm.manage.controller;

import com.huitai.bpm.manage.entity.FlwDeployment;
import com.huitai.bpm.manage.entity.FlwPage;
import com.huitai.bpm.manage.service.FlwDeployService;
import com.huitai.common.model.Result;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description: 流程部署实施请求处理层 <br>
 * @author PLF <br>
 * @date 2020-12-01 9:00 <br>
 * @version 1.0 <br>
 */
@RestController
@RequestMapping("/flw/manage/deploy")
public class FlwDeployController {

    @Autowired
    private FlwDeployService flwDeployService;

    @PostMapping("add")
    public Result add(@RequestParam("file") MultipartFile file) throws IOException {
        Deployment deploy = flwDeployService.add(file);
        return Result.ok(deploy);
    }

    @PostMapping("page")
    public Result page(FlwPage<FlwDeployment, List<FlwDeployment>> flwPage, @RequestBody FlwDeployment flwDeployment) {
        FlwPage<FlwDeployment, List<FlwDeployment>> deployed = flwDeployService.page(flwPage, flwDeployment);
        return Result.ok(deployed);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody List<String> list) {
        flwDeployService.deleteDeployed(list, true);
        return Result.ok();
    }

    @PutMapping("suspend/{key}")
    public Result suspend(@PathVariable String key) {
        flwDeployService.suspendProcess(key, true);
        return Result.ok();
    }

    @PutMapping("activate/{key}")
    public Result activate(@PathVariable String key) {
        flwDeployService.activateProcess(key, true);
        return Result.ok();
    }

}
