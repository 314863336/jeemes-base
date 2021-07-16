package com.huitai.core.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.message.utils.FreeMarkerUtil;
import com.huitai.core.message.utils.MessageConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
@ApiModel(value="HtMessageSend对象", description="消息表")
public class HtMessageSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "消息类型（1-系统 2-短信 3-邮件 4-微信）")
    @Length(max=1, message="消息类型（1-系统 2-短信 3-邮件 4-微信）长度不能超过1个字符")
    private String msgType;

    @ApiModelProperty(value = "消息标题")
    @Length(max=200, message="消息标题长度不能超过200个字符")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容")
    @Length(max=200, message="消息内容长度不能超过200个字符")
    private String msgContent;

    @ApiModelProperty(value = "接收人")
    @Length(max=64, message="接收人长度不能超过64个字符")
    private String receiveUser;

    @Length(max=64, message="长度不能超过64个字符")
    private String sendUser;

    private Date sendDate;

    @ApiModelProperty(value = "推送状态（0-未推送 1-成功  2失败）")
    @Length(max=1, message="推送状态（0-未推送 1-成功  2失败）长度不能超过1个字符")
    private String pushStatus;

    @ApiModelProperty(value = "推送次数")
    private Integer pushNumber;

    @ApiModelProperty(value = "推送返回结果码")
    @Length(max=200, message="推送返回结果码长度不能超过200个字符")
    private String pushReturnCode;

    @ApiModelProperty(value = "推送返回的消息内容")
    @Length(max=200, message="推送返回的消息内容长度不能超过200个字符")
    private String pushReturnContent;

    @ApiModelProperty(value = "备注")
    @Length(max=300, message="备注长度不能超过300个字符")
    private String remarks;

    @TableField(exist = false)
    private String receiveUserName;

    // 发送邮件时使用
    @TableField(exist = false)
    private MessageConverter messageConverter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

    // 渲染content
    public void setMsgContent(Map<String, Object> map) {
        this.msgContent = FreeMarkerUtil.convert(map, this.getMsgContent());
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
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }
    public Integer getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Integer pushNumber) {
        this.pushNumber = pushNumber;
    }
    public String getPushReturnCode() {
        return pushReturnCode;
    }

    public void setPushReturnCode(String pushReturnCode) {
        this.pushReturnCode = pushReturnCode;
    }
    public String getPushReturnContent() {
        return pushReturnContent;
    }

    public void setPushReturnContent(String pushReturnContent) {
        this.pushReturnContent = pushReturnContent;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "HtMessageSend{" +
            "id=" + id +
            ", msgType=" + msgType +
            ", msgTitle=" + msgTitle +
            ", msgContent=" + msgContent +
            ", receiveUser=" + receiveUser +
            ", sendUser=" + sendUser +
            ", sendDate=" + sendDate +
            ", pushStatus=" + pushStatus +
            ", pushNumber=" + pushNumber +
            ", pushReturnCode=" + pushReturnCode +
            ", pushReturnContent=" + pushReturnContent +
            ", remarks=" + remarks +
        "}";
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    // 若使用接收人系统绑定邮箱,to设为null
    public void setMessageConverter(String to) {
        this.messageConverter = new MessageConverter(to, this.msgTitle, this.msgContent, null, true);
    }

    // map目的为渲染msgContent
    // 若使用接收人系统绑定邮箱,to设为null
    public void setMessageConverter(String to, Map<String, Object> map) {

        this.messageConverter = new MessageConverter(to, this.msgTitle, this.msgContent, map, true);
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }
}
