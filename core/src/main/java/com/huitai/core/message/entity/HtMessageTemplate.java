package com.huitai.core.message.entity;

import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * <p>
 * 消息模板
 * </p>
 *
 * @author XJM
 * @since 2020-04-30
 */
@ApiModel(value="HtMessageTemplate对象", description="消息模板")
public class HtMessageTemplate extends BaseEntity {

    @ApiModelProperty(value = "模板消息标题")
    @Length(max=200, message="模板消息标题长度不能超过200个字符")
    private String tplTitle;

    @ApiModelProperty(value = "模板编码")
    @Length(max=100, message="模板编码长度不能超过100个字符")
    private String tplCode;

    @ApiModelProperty(value = "模板消息类型")
    @Length(max=1, message="模板消息类型长度不能超过1个字符")
    private String tplType;

    @ApiModelProperty(value = "模板消息内容")
    private String tplContent;

    @ApiModelProperty(value = "状态")
    @Length(max=1, message="状态长度不能超过1个字符")
    private String status;

    @ApiModelProperty(value = "备注信息")
    @Length(max=500, message="备注信息长度不能超过500个字符")
    private String remarks;

    @ApiModelProperty(value = "创建者主键")
    @Length(max=64, message="创建者主键长度不能超过64个字符")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "修改者主键")
    @Length(max=64, message="修改者主键长度不能超过64个字符")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    public String getTplTitle() {
        return tplTitle;
    }

    public void setTplTitle(String tplTitle) {
        this.tplTitle = tplTitle;
    }
    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }
    public String getTplType() {
        return tplType;
    }

    public void setTplType(String tplType) {
        this.tplType = tplType;
    }
    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public String toString() {
        return "HtMessageTemplate{" +
            ", tplTitle=" + tplTitle +
            ", tplCode=" + tplCode +
            ", tplType=" + tplType +
            ", tplContent=" + tplContent +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }
}
