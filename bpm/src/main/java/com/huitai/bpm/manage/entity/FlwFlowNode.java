package com.huitai.bpm.manage.entity;

import java.io.Serializable;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 模型节点对象 <br>
 * @date 2021-01-05 9:29 <br>
 */
public class FlwFlowNode implements Serializable {
    private String id;
    private String name;

    public FlwFlowNode() {
    }

    public FlwFlowNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
