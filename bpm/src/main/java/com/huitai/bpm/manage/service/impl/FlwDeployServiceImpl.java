package com.huitai.bpm.manage.service.impl;

import com.huitai.bpm.manage.entity.FlwDeployment;
import com.huitai.bpm.manage.entity.FlwPage;
import com.huitai.bpm.manage.service.FlwDeployService;
import com.huitai.common.utils.StringUtil;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 流程部署实施服务接口实现 <br>
 * @date 2020-12-01 9:02 <br>
 */
@Transactional(readOnly = true)
@Service
public class FlwDeployServiceImpl implements FlwDeployService {

    @Autowired
    private RepositoryService repositoryService;

    @Transactional(readOnly = false)
    @Override
    public Deployment add(MultipartFile file) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        return repositoryService
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    @Override
    public FlwPage<FlwDeployment, List<FlwDeployment>> page(FlwPage<FlwDeployment, List<FlwDeployment>> flwPage, FlwDeployment flwDeployment) {

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if (StringUtil.isNotBlank(flwDeployment.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + flwDeployment.getName() + "%");
        }
        if (StringUtil.isNotBlank(flwDeployment.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + flwDeployment.getKey() + "%");
        }
        if (StringUtil.isNotBlank(flwDeployment.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + flwDeployment.getCategory() + "%");
        }

        int maxResults = new Long(flwPage.getSize()).intValue();

        int firstResult = ((new Long(flwPage.getCurrent()).intValue()) - 1) * maxResults;

        List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(firstResult, maxResults);

        long count = processDefinitionQuery.count();

        flwPage.setTotal(count);

        List<FlwDeployment> flwDeployments = processDefinitions
                .stream()
                .map(processDefinition -> {
                    ProcessDefinitionEntity entity = (ProcessDefinitionEntity) processDefinition;
                    return new FlwDeployment(entity.getDeploymentId(), entity.getName(), entity.getKey(), entity.getCategory(), entity.getVersion(), entity.getSuspensionState());
                })
                .collect(Collectors.toList());

        flwPage.setList(flwDeployments);

        return flwPage;
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteDeployed(List<String> list, boolean b) {
        list.forEach(id -> {
            repositoryService.deleteDeployment(id, b);
        });
    }

    @Transactional(readOnly = false)
    @Override
    public void suspendProcess(String key, boolean b) {
        repositoryService.suspendProcessDefinitionByKey(key, b, null);
    }

    @Transactional(readOnly = false)
    @Override
    public void activateProcess(String key, boolean b) {
        repositoryService.activateProcessDefinitionByKey(key, b, null);
    }
}
