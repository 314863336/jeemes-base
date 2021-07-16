package com.huitai.bpm.designer.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description: 工作流模型映射实体 <br>
 * @author PLF <br>
 * @date 2020-11-27 10:26 <br>
 * @version 1.0 <br>
 */
public class FlwModel implements Serializable {

    private String id;
    @NotBlank(message = "模型名称不能为空")
    @Length(max=2, message="模型名称长度不能超过2个字符")
    private String name;
    @NotBlank(message = "key不能为空")
    private String key;
    @NotBlank(message = "revision不能为空")
    private Integer revision;
    private String description;
    @NotBlank(message = "jsonXml不能为空")
    private String jsonXml;
    @NotBlank(message = "svgXml不能为空")
    private String svgXml;

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

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJsonXml() {
        return jsonXml;
    }

    public void setJsonXml(String jsonXml) {
        this.jsonXml = jsonXml;
    }

    public String getSvgXml() {
        return svgXml;
    }

    public void setSvgXml(String svgXml) {
        this.svgXml = svgXml;
    }
}
