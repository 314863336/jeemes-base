package com.huitai.bpm.manage.entity;

import java.io.Serializable;

/**
 * @description: 流程部署映射实体 <br>
 * @author PLF <br>
 * @date 2020-11-30 18:54 <br>
 * @version 1.0 <br>
 */
public class FlwDeployment implements Serializable {

    private String id;
    private String name;
    private String key;
    private String category;
    private Integer version;
    private Integer suspensionState;

    public FlwDeployment() {
    }

    public FlwDeployment(String id, String name, String key, String category, Integer version, Integer suspensionState) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.category = category;
        this.version = version;
        this.suspensionState = suspensionState;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(Integer suspensionState) {
        this.suspensionState = suspensionState;
    }

    @Override
    public String toString() {
        return "FlwDeployment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", category='" + category + '\'' +
                ", version=" + version +
                ", suspensionState=" + suspensionState +
                '}';
    }
}
