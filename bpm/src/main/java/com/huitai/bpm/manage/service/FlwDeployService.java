package com.huitai.bpm.manage.service;

import com.huitai.bpm.manage.entity.FlwDeployment;
import com.huitai.bpm.manage.entity.FlwPage;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description: 流程部署实施服务层接口 <br>
 * @author PLF <br>
 * @date 2020-12-01 9:01 <br>
 * @version 1.0 <br>
 */
public interface FlwDeployService {

    /**
     * @description: 流程部署，半自动模式 <br>
     * @param: file 包含二进制上传文件数据和文件名称 <br>
     * @return: Deployment 部署实体 <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2020-11-30 17:14 <br>
     */
    Deployment add(MultipartFile file) throws IOException;

    /**
     * @description: 获取已经部署的流程 <br>
     * @param: flwPage 工作流分页实体，包含分页查询参数 <br>
     * @param: flwDeployment 实体对象，包含过滤参数 <br>
     * @return: flwPage 工作流分页实体，包含List数据集 <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2020-11-30 18:34 <br>
     */
    FlwPage<FlwDeployment, List<FlwDeployment>> page(FlwPage<FlwDeployment, List<FlwDeployment>> flwPage, FlwDeployment flwDeployment);

    /**
     * @description: 删除已经部署的流程，支持单个和批量 <br>
     * @param: list 删除的部署主键集 <br>
     * @param: b 值为true表示级联删除 <br>
     * @return:  <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2020-12-01 17:30 <br>
     */
    void deleteDeployed(List<String> list, boolean b);

    /**
     * @description: 流程挂起（支持此流程的所有正在运行的流程一同被挂起） <br>
     * @param: key 流程定义键 <br>
     * @param: b 级联作用到所有流程实例 <br>
     * @return:  <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2020-12-08 17:09 <br>
     */
    void suspendProcess(String key, boolean b);

    /**
     * @description: 流程激活（支持此流程的所有正在运行的流程一同被激活） <br>
     * @param: key 流程定义键 <br>
     * @return: b 级联作用到所有流程实例 <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2020-12-08 18:58 <br>
     */
    void activateProcess(String key, boolean b);
}
