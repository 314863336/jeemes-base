package com.huitai.core.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统接收消息表
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
@ApiModel(value="HtMessageReceive对象", description="接收消息表")
public class HtMessageReceive implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @Length(max=64, message="长度不能超过64个字符")
    private String sendId;

    @ApiModelProperty(value = "消息标题")
    @Length(max=200, message="消息标题长度不能超过200个字符")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "接收人")
    @Length(max=64, message="接收人长度不能超过64个字符")
    private String receiveUser;

    @Length(max=64, message="长度不能超过64个字符")
    private String sendUser;

    private Date receiveDate;

    @ApiModelProperty(value = "读取状态（1已读 2未读）")
    @Length(max=1, message="读取状态（1已读 2未读）长度不能超过1个字符")
    private String readStatus;

    @ApiModelProperty(value = "消息类型（0-PC消息 1-APP消息）")
    @Length(max=1, message="消息类型（0-PC消息 1-APP消息）长度不能超过1个字符")
    private String type;

    @ApiModelProperty(value = "读取时间")
    private Date readDate;

    @ApiModelProperty(value = "备注")
    @Length(max=300, message="备注长度不能超过300个字符")
    private String remarks;

    @TableField(exist = false)
    private String sendUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }
    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }
    public String getReadStatus() {
        return readStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "HtMessageReceive{" +
            "id=" + id +
            ", msgTitle=" + msgTitle +
            ", msgContent=" + msgContent +
            ", receiveUser=" + receiveUser +
            ", sendUser=" + sendUser +
            ", receiveDate=" + receiveDate +
            ", readStatus=" + readStatus +
            ", readDate=" + readDate +
            ", remarks=" + remarks +
        "}";
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}
