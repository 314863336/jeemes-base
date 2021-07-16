package com.huitai.core.message.dao;

import com.huitai.common.utils.Page;
import com.huitai.core.message.entity.HtMessageSend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
public interface HtMessageSendDao extends BaseMapper<HtMessageSend> {

    /**
     * description: 获取分页数据 <br>
     * version: 1.0 <br>
     * date: 2020/5/11 9:03 <br>
     * author: XJM <br>
     */
    Page<HtMessageSend> findHtMessageSendList(Page<HtMessageSend> page);

}
