package com.huitai.bpm.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 待办任务实体 <br>
 * @date 2021-01-22 10:24 <br>
 */
public class FlwTodoEntity implements Serializable {
    private FlwDeployment flwDeployment;
    private String createBy;
    private FlwTask flwTask;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    public FlwTodoEntity() {
    }

    public FlwTodoEntity(FlwDeployment flwDeployment, String createBy, FlwTask flwTask, Date startTime) {
        this.flwDeployment = flwDeployment;
        this.createBy = createBy;
        this.flwTask = flwTask;
        this.startTime = startTime;
    }

    public FlwDeployment getFlwDeployment() {
        return flwDeployment;
    }

    public void setFlwDeployment(FlwDeployment flwDeployment) {
        this.flwDeployment = flwDeployment;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public FlwTask getFlwTask() {
        return flwTask;
    }

    public void setFlwTask(FlwTask flwTask) {
        this.flwTask = flwTask;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
