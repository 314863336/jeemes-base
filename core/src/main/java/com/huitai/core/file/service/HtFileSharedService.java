package com.huitai.core.file.service;

import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseIService;
import com.huitai.core.file.entity.HtFileShared;

import java.util.List;

/**
 * <p>
 * 文档共享表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-05-28
 */
public interface HtFileSharedService extends BaseIService<HtFileShared> {

    /**
     * description: 获取我分享的文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/28 15:45 <br>
     * author: XJM <br>
     */
    Page<HtFileShared> findFormMeList(Page<HtFileShared> page);

    /**
     * description: 分享文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/29 16:28 <br>
     * author: XJM <br>
     */
    void shareFile(List<HtFileShared> htFileShareds);

    /**
     * description: 取消分享 <br>
     * version: 1.0 <br>
     * date: 2020/6/2 9:19 <br>
     * author: XJM <br>
     */
    void deleteShared(String id);
}
