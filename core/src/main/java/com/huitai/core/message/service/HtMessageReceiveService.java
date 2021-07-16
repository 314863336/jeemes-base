package com.huitai.core.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.common.utils.Page;
import com.huitai.core.message.entity.HtMessageReceive;
import com.huitai.core.message.entity.HtMessageSend;

import java.util.List;

/**
 * <p>
 * 系统接收消息表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
public interface HtMessageReceiveService extends IService<HtMessageReceive> {

    // 未推送
    String READ_STATUS_0 = "0";

    // 已读
    String READ_STATUS_1 = "1";

    // 未读
    String READ_STATUS_2 = "2";

    /**
     * description: 根据发送消息创建接收消息 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 10:24 <br>
     * author: XJM <br>
     */
    void createReceiveMessage(HtMessageSend htMessageSend);

    /**
     * description: 查询接收消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/8 16:27 <br>
     * author: XJM <br>
     */
    Page<HtMessageReceive> findHtMessageReceiveList(Page<HtMessageReceive> page);

    /**
     * description: 获取新消息 <br>
     * version: 1.0 <br>
     * date: 2020/5/9 17:06 <br>
     * author: XJM <br>
     */
    List<HtMessageReceive> getNewMessage();

    /**
     * description: 获取未推送消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/9 17:22 <br>
     * author: XJM <br>
     */
    List<HtMessageReceive> findNewHtMessageReceiveList(HtMessageReceive htMessageReceive);

    /**
     * description: 修改已读状态 <br>
     * version: 1.0 <br>
     * date: 2020/5/11 9:59 <br>
     * author: XJM <br>
     */
    void changeReadStatus(String[] ids);

}
