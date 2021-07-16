package com.huitai.core.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>
 * 文档信息表
 * </p>
 *
 * @author TYJ
 * @since 2020-04-30
 */
@ApiModel(value="HtFileInfo对象", description="文档信息表")
public class HtFileInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "上级文件")
    @Length(max=64, message="上级文件长度不能超过64个字符")
    private String parentId;

    @ApiModelProperty(value = "名称")
    @Length(max=255, message="名称长度不能超过255个字符")
    private String fileName;

    @ApiModelProperty(value = "文件类型（1-文件夹 2-文件）")
    @Length(max=1, message="文件类型（1-文件夹 2-文件）长度不能超过1个字符")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    @Length(max=64, message="文件大小长度不能超过64个字符")
    private String fileSize;

    @ApiModelProperty(value = "文件uuid")
    @Length(max=64, message="文件uuid长度不能超过64个字符")
    private String fileId;

    @ApiModelProperty(value = "文件保存地址")
    @Length(max=255, message="文件保存地址长度不能超过255个字符")
    private String address;

    @ApiModelProperty(value = "状态（0-正常 2-停用）")
    @Length(max=1, message="状态（0-正常 2-停用）长度不能超过1个字符")
    private String status;

    @ApiModelProperty(value = "业务主键")
    @Length(max=64, message="业务主键长度不能超过64个字符")
    private String bizKey;

    @ApiModelProperty(value = "业务类型")
    @Length(max=64, message="业务类型长度不能超过64个字符")
    private String bizType;

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    @ApiModelProperty(value = "上传者")
    @TableField(exist=false)
    private String uploadUserName;

    @ApiModelProperty(value = "子文件夹")
    @TableField(exist=false)
    private List<HtFileInfo> children;

    public List<HtFileInfo> getChildren() {
        return children;
    }

    public void setChildren(List<HtFileInfo> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }
    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        return "HtFileInfo{" +
            "id=" + id +
            ", parentId=" + parentId +
            ", fileName=" + fileName +
            ", fileType=" + fileType +
            ", fileSize=" + fileSize +
            ", fileId=" + fileId +
            ", address=" + address +
            ", status=" + status +
            ", bizKey=" + bizKey +
            ", bizType=" + bizType +
        "}";
    }
}
