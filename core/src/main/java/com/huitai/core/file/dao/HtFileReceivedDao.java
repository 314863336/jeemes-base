package com.huitai.core.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.common.utils.Page;
import com.huitai.core.file.entity.HtFileReceived;

/**
 * <p>
 * 文档共享接收表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-05-29
 */
public interface HtFileReceivedDao extends BaseMapper<HtFileReceived> {

    /**
     * description: 获取分享给我文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/28 15:45 <br>
     * author: XJM <br>
     */
    Page<HtFileReceived> findToMeList(Page<HtFileReceived> page);

}
