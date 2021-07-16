package com.huitai.bpm.manage.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 审批意见实体 <br>
 * @date 2020-12-17 17:03 <br>
 */
public class FlwComment implements Serializable {
    private String name;
    private Date startTime;
    private Date endTime;
    private String consume;
    private String assignee;
    private String message;

    public FlwComment() {
    }

    public FlwComment(String name, Date startTime, Date endTime, String consume, String assignee, String message) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.consume = consume;
        this.assignee = assignee;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FlwComment{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", consume='" + consume + '\'' +
                ", assignee='" + assignee + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
