package com.huitai.core.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.common.utils.Page;
import com.huitai.core.message.entity.HtMessageSend;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
public interface HtMessageSendService extends IService<HtMessageSend> {

    // 系统消息
    String MSG_TYPE_1 = "1";

    // 短信消息
    String MSG_TYPE_2 = "2";

    // 邮件消息
    String MSG_TYPE_3 = "3";

    // 微信消息
    String MSG_TYPE_4 = "4";

    // 推送状态 未推送
    String PUSH_STATUS_0 = "0";

    // 推送状态 成功
    String PUSH_STATUS_1 = "1";

    // 推送状态 失败
    String PUSH_STATUS_2 = "2";


    /**
     * description: 发送消息 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 10:08 <br>
     * author: XJM <br>
     */
    void sendMessage(HtMessageSend htMessageSend);

    /**
     * description: 获取分页数据 <br>
     * version: 1.0 <br>
     * date: 2020/5/11 9:03 <br>
     * author: XJM <br>
     */
    Page<HtMessageSend> findHtMessageSendList(Page<HtMessageSend> page);
}
