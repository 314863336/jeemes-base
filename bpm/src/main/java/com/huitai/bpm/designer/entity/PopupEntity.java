package com.huitai.bpm.designer.entity;

import java.io.Serializable;

/**
 * description: PopupEntity <br>
 * date: 2020/8/26 19:18 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public class PopupEntity implements Serializable {

    private String nodeId;
    private String nodeName;
    private String assignee;
    private String startTime;
    private String endTime;

    public PopupEntity() {
    }

    public PopupEntity(String nodeId, String nodeName, String assignee, String startTime, String endTime) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.assignee = assignee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
