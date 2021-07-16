package com.huitai.core.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * <p>
 * 文档共享表
 * </p>
 *
 * @author XJM
 * @since 2020-05-28
 */
@ApiModel(value="HtFileShared对象", description="文档共享表")
public class HtFileShared extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "上级文件")
    @Length(max=64, message="上级文件长度不能超过64个字符")
    private String fileInfoId;

    @ApiModelProperty(value = "分享人")
    @Length(max=64, message="分享人长度不能超过64个字符")
    private String fromUserId;

    // 数据库中为了准确的查询
    @ApiModelProperty(value = "接收人,多个以,隔开")
    @Length(max=2000, message="接收人,多个以,隔开长度不能超过2000个字符")
    private String toUserIds;

    // 该字段是为了更方便的显示用户名
    @ApiModelProperty(value = "接收人名称,多个以,隔开")
    @Length(max=2000, message="接收人名称,多个以,隔开长度不能超过2000个字符")
    private String toUserNames;

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
    private String fromUserName;  // 分享人昵称

    @TableField(exist = false)
    private HtFileInfo htFileInfo;  // 文件信息


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
    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }
    public String getToUserNames() {
        return toUserNames;
    }

    public void setToUserNames(String toUserNames) {
        this.toUserNames = toUserNames;
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
        return "HtFileShared{" +
            "id=" + id +
            ", fileInfoId=" + fileInfoId +
            ", fromUserId=" + fromUserId +
            ", toUserIds=" + toUserIds +
            ", toUserNames=" + toUserNames +
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
}
