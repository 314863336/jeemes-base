package com.huitai.core.file.service;

import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseIService;
import com.huitai.core.file.entity.HtFileReceived;

/**
 * <p>
 * 文档共享接收表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-05-29
 */
public interface HtFileReceivedService extends BaseIService<HtFileReceived> {


    /**
     * description: 获取分享给我文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/28 15:45 <br>
     * author: XJM <br>
     */
    Page<HtFileReceived> findToMeList(Page<HtFileReceived> page);
}
