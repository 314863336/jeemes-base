package com.huitai.core.job.entity;

import com.huitai.core.base.BaseEntityD;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 作业调度日志表
 * </p>
 *
 * @author TYJ
 * @since 2020-05-29
 */
@ApiModel(value="HtSysJobLog对象", description="作业调度日志表")
public class HtSysJobLog extends BaseEntityD {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "任务名称")
    @NotBlank(message="任务名称不能为空")
    @Length(max=64, message="任务名称长度不能超过64个字符")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    @NotBlank(message="任务组名不能为空")
    @Length(max=64, message="任务组名长度不能超过64个字符")
    private String jobGroup;

    @ApiModelProperty(value = "日志类型")
    @Length(max=50, message="日志类型长度不能超过50个字符")
    private String jobType;

    @ApiModelProperty(value = "日志事件")
    @Length(max=200, message="日志事件长度不能超过200个字符")
    private String jobEvent;

    @ApiModelProperty(value = "日志信息")
    @Length(max=500, message="日志信息长度不能超过500个字符")
    private String jobMessage;

    @ApiModelProperty(value = "是否异常")
    @Length(max=1, message="是否异常长度不能超过1个字符")
    private String isException;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
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
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    public String getJobEvent() {
        return jobEvent;
    }

    public void setJobEvent(String jobEvent) {
        this.jobEvent = jobEvent;
    }
    public String getJobMessage() {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }
    public String getIsException() {
        return isException;
    }

    public void setIsException(String isException) {
        this.isException = isException;
    }
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    @Override
    public String toString() {
        return "HtSysJobLog{" +
            "id=" + id +
            ", jobName=" + jobName +
            ", jobGroup=" + jobGroup +
            ", jobType=" + jobType +
            ", jobEvent=" + jobEvent +
            ", jobMessage=" + jobMessage +
            ", isException=" + isException +
            ", exceptionInfo=" + exceptionInfo +
            ", createDate=" + createDate +
        "}";
    }
}
