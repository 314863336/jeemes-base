package com.huitai.core.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * <p>
 * 文档共享接收表
 * </p>
 *
 * @author XJM
 * @since 2020-05-29
 */
@ApiModel(value="HtFileReceived对象", description="文档共享接收表")
public class HtFileReceived extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "文件")
    @Length(max=64, message="文件长度不能超过64个字符")
    private String fileInfoId;

    @ApiModelProperty(value = "分享人")
    @Length(max=64, message="分享人长度不能超过64个字符")
    private String fromUserId;

    @ApiModelProperty(value = "接收人")
    @Length(max=2000, message="接收人长度不能超过2000个字符")
    private String toUserId;

    @ApiModelProperty(value = "分享主键")
    @Length(max=64, message="分享主键长度不能超过64个字符")
    private String shareId;

    @ApiModelProperty(value = "状态（0-正常 2-停用）")
    @Length(max=1, message="状态（0-正常 2-停用）长度不能超过1个字符")
    private String status;

    @ApiModelProperty(value = "备注")
    @Length(max=500, message="备注长度不能超过500个字符")
    private String remarks;

    @ApiModelProperty(value = "上传者")
    @Length(max=64, message="上传者长度不能超过64个字符")
    private String createBy;

    @ApiModelProperty(value = "上传时间")
    private Date createDate;

    @ApiModelProperty(value = "更新者")
    @Length(max=64, message="更新者长度不能超过64个字符")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @TableField(exist = false)
    private String fromUserName;

    @TableField(exist = false)
    private HtFileInfo htFileInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(String fileInfoId) {
        this.fileInfoId = fileInfoId;
    }
    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }
    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
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
        return "HtFileReceived{" +
            "id=" + id +
            ", fileInfoId=" + fileInfoId +
            ", fromUserId=" + fromUserId +
            ", toUserId=" + toUserId +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public HtFileInfo getHtFileInfo() {
        return htFileInfo;
    }

    public void setHtFileInfo(HtFileInfo htFileInfo) {
        this.htFileInfo = htFileInfo;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }
}
