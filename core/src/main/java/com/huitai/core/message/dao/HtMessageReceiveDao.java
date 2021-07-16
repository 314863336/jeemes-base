package com.huitai.core.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.common.utils.Page;
import com.huitai.core.message.entity.HtMessageReceive;

import java.util.List;

/**
 * <p>
 * 接收消息表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-05-07
 */
public interface HtMessageReceiveDao extends BaseMapper<HtMessageReceive> {

    /**
     * description: 查询接收消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/8 16:27 <br>
     * author: XJM <br>
     */
    Page<HtMessageReceive> findHtMessageReceiveList(Page<HtMessageReceive> page);

    /**
     * description: 获取未推送消息列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/9 17:22 <br>
     * author: XJM <br>
     */
    List<HtMessageReceive> findNewHtMessageReceiveList(HtMessageReceive htMessageReceive);

}
