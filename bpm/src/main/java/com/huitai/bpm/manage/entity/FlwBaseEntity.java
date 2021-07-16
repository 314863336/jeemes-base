package com.huitai.bpm.manage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 工作流基础类 <br>
 * @date 2020-12-03 9:12 <br>
 */
public class FlwBaseEntity extends BaseEntity {

    /*流程属性*/

    @ApiModelProperty(value = "流程实例外键")
    @ExcelIgnore
    private String processInstanceId;

    @ApiModelProperty(value = "业务流程参与人，多人逗号隔开")
    @ExcelIgnore
    private String assignees;

    @ApiModelProperty(value = "当前流程主人（当前待办人），多个逗号隔开")
    @ExcelIgnore
    private String owners;

    @ApiModelProperty(value = "业务再分类，同一套三层代码绑定不同业务和流程时用到")
    @TableField(exist = false)
    @ExcelIgnore
    private String businessType;

    @ApiModelProperty(value = "流程办理时的意见")
    @TableField(exist = false)
    @ExcelIgnore
    private String comment;

    public FlwBaseEntity() {
    }

    public FlwBaseEntity(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /*getter、setter*/

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignees() {
        return assignees;
    }

    public void setAssignees(String assignees) {
        this.assignees = assignees;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
