package com.huitai.bpm.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 任务节点对象 <br>
 * @date 2021-01-14 18:31 <br>
 */
public class FlwTask implements Serializable {
    private String id;
    private String processInstanceId;
    private String definitionKey;
    private String name;
    private String assignee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String groups;
    private String users;
    private String loginCode;
    private String userName;

    public FlwTask() {
    }

    public FlwTask(String id, String name, String assignee, Date createTime) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.createTime = createTime;
    }

    public FlwTask(String id, String processInstanceId, String definitionKey, String name, String assignee, Date createTime, String users, String groups) {
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.definitionKey = definitionKey;
        this.name = name;
        this.assignee = assignee;
        this.createTime = createTime;
        this.users = users;
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getDefinitionKey() {
        return definitionKey;
    }

    public void setDefinitionKey(String definitionKey) {
        this.definitionKey = definitionKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
