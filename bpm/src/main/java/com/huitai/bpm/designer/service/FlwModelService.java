package com.huitai.bpm.designer.service;

import java.util.Map;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 工作流模型服务层接口 <br>
 * @date 2020-11-27 10:40 <br>
 */
public interface FlwModelService {

    String MODEL_ID = "modelId";
    String MODEL_NAME = "name";
    String MODEL_REVISION = "revision";
    String MODEL_DESCRIPTION = "description";
    String REFLECTIONS_FORPACKAGES = "com.huitai";
    String LOGIN_CODE = "login_code";
    String FRILLSSVG_BPMNXML = "bpmnXml";
    String FRILLSSVG_HIGHPOINT = "highPoint";
    String FRILLSSVG_HIGHLINE = "highLine";
    String FRILLSSVG_WAITINGTODO = "waitingToDo";
    String FRILLSSVG_IDO = "iDo";
    String FRILLSSVG_NODEMAP = "nodeMap";

    /**
     * @description: 查询流程Key <br>
     * @param: <br>
     * @return: Map 流程启动键 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-04 15:28 <br>
     */
    Map<String, String> findProcessKeys();

    /**
     * @description: 根据流程定义主键获取流程图 <br>
     * @param: processDefinitionId 流程定义主键 <br>
     * @return: String 返回模型的xml字符串 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-09 9:06 <br>
     */
    String noFrillsSvg(String processDefinitionId);

    /**
     * @description: 根据流程实例主键获取有状态的流程图 <br>
     * @param: processInstanceId 流程实例主键 <br>
     * @return: Map 包含流程图和轨迹状态数据 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2020-12-14 18:54 <br>
     */
    Map<String, Object> frillsSvg(String processInstanceId);
}
