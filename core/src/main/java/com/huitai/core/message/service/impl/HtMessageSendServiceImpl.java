package com.huitai.core.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.message.dao.HtMessageSendDao;
import com.huitai.core.message.entity.HtMessageSend;
import com.huitai.core.message.service.HtMessageReceiveService;
import com.huitai.core.message.service.HtMessageSendService;
import com.huitai.core.message.utils.MailService;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysUserService;
import com.huitai.core.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
@Service
public class HtMessageSendServiceImpl extends ServiceImpl<HtMessageSendDao, HtMessageSend> implements HtMessageSendService {

    Logger logger = LoggerFactory.getLogger(HtMessageSendServiceImpl.class);

    @Autowired
    private HtMessageReceiveService htMessageReceiveService;

    @Autowired
    private MailService mailService;

    @Autowired
    private HtSysUserService htSysUserService;

    /**
     * description: 发送消息，重发时需要传递数据库存在的消息对象 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 10:08 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public synchronized void sendMessage(HtMessageSend htMessageSend){
        HtSysUser htSysUser = UserUtil.getCurUser();

        //PushReturnCode 暂时和PushStatus一致
        // 重新发送
        if(StringUtil.isNotBlank(htMessageSend.getId())){
            htMessageSend.setPushNumber(htMessageSend.getPushNumber() + 1);
            htMessageSend.setSendDate(new Date());
            htMessageSend.setSendUser(htSysUser.getId());
            htMessageSend.setPushReturnCode(HtMessageSendService.PUSH_STATUS_1);
            htMessageSend.setPushReturnCode(null);
            super.updateById(htMessageSend);

        }else{
            // 新发送
            htMessageSend.setPushNumber(1);
            htMessageSend.setPushStatus(HtMessageSendService.PUSH_STATUS_1);
            htMessageSend.setSendDate(new Date());
            htMessageSend.setSendUser(htSysUser.getId());
            htMessageSend.setPushReturnCode(HtMessageSendService.PUSH_STATUS_1);
            super.save(htMessageSend);
        }

        // 如果是系统消息
        if(HtMessageSendService.MSG_TYPE_1.equals(htMessageSend.getMsgType())){
            // 保存系统接收消息
            htMessageReceiveService.createReceiveMessage(htMessageSend);
        }else if(HtMessageSendService.MSG_TYPE_3.equals(htMessageSend.getMsgType())){
            HtSysUser receiveUser = htSysUserService.getById(htMessageSend.getReceiveUser());
            // 发送邮件
            try {
                // 如果不传递发送邮箱，则这里使用发送用户的邮箱
                if(StringUtil.isBlank(htMessageSend.getMessageConverter().getTo())){
                    htMessageSend.getMessageConverter().setTo(receiveUser.getEmail());
                }
                mailService.sendEmail(htMessageSend.getMessageConverter());
            } catch (UnsupportedEncodingException | MessagingException e) {
                logger.error("发送邮件失败", e);
                htMessageSend.setPushStatus(HtMessageSendService.PUSH_STATUS_2);
                htMessageSend.setPushReturnContent(e.getMessage());
                htMessageSend.setPushReturnCode(HtMessageSendService.PUSH_STATUS_2);
                super.updateById(htMessageSend);
                return;
            }

            // 如果是邮件发送
            createEmailMessageSend(htMessageSend, htSysUser);
            // 保存系统接收消息，通知收到邮件请查收
            htMessageReceiveService.createReceiveMessage(htMessageSend);
        }
    }

    /**
     * description: 获取分页数据 <br>
     * version: 1.0 <br>
     * date: 2020/5/11 9:03 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public Page<HtMessageSend> findHtMessageSendList(Page<HtMessageSend> page){
        if(page.getParams() == null){
            page.setParams(new HtMessageSend());
        }
        return baseMapper.findHtMessageSendList(page);
    }



    private void createEmailMessageSend(HtMessageSend htMessageSend, HtSysUser htSysUser){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("您绑定的邮箱收到一封邮件, 发送人: ");
        stringBuffer.append(htSysUser.getUserName());
        stringBuffer.append(",标题: ");
        stringBuffer.append(htMessageSend.getMsgTitle() + ",请注意查收!");
        // 修改发送内容和标题
        htMessageSend.setMsgContent(stringBuffer.toString());
        htMessageSend.setMsgTitle("收到邮件,请查收");
    }
}
