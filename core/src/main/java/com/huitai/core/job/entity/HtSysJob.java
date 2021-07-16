package com.huitai.core.job.entity;

import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 作业调度表
 * </p>
 *
 * @author TYJ
 * @since 2020-05-25
 */
@ApiModel(value="HtSysJob对象", description="作业调度表")
public class HtSysJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    @NotBlank(message="任务组名不能为空")
    @Length(max=64, message="任务组名长度不能超过64个字符")
    private String jobGroup;

    @ApiModelProperty(value = "任务描述")
    @NotBlank(message="任务描述不能为空")
    @Length(max=100, message="任务描述长度不能超过100个字符")
    private String description;

    @ApiModelProperty(value = "调用目标字符串")
    @NotBlank(message="调用目标字符串不能为空")
    @Length(max=1000, message="调用目标字符串长度不能超过1000个字符")
    private String invokeTarget;

    @ApiModelProperty(value = "任务参数")
    private String params;

    @ApiModelProperty(value = "Cron执行表达式")
    @NotBlank(message="Cron执行表达式不能为空")
    @Length(max=255, message="Cron执行表达式长度不能超过255个字符")
    private String cronExpression;

    @ApiModelProperty(value = "计划执行错误策略")
    private BigDecimal misfireInstruction;

    @ApiModelProperty(value = "是否并发执行")
    @Length(max=1, message="是否并发执行长度不能超过1个字符")
    private String concurrent;

    @ApiModelProperty(value = "状态（0正常 1删除 2暂停）")
    private String status;

    @ApiModelProperty(value = "创建者")
    @Length(max=64, message="创建者长度不能超过64个字符")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新者")
    @Length(max=64, message="更新者长度不能超过64个字符")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getInvokeTarget() {
        return invokeTarget;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    public BigDecimal getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(BigDecimal misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }
    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "HtSysJob{" +
            "jobName=" + jobName +
            ", jobGroup=" + jobGroup +
            ", description=" + description +
            ", invokeTarget=" + invokeTarget +
            ", cronExpression=" + cronExpression +
            ", misfireInstruction=" + misfireInstruction +
            ", concurrent=" + concurrent +
            ", status=" + status +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
            ", remarks=" + remarks +
        "}";
    }
}
