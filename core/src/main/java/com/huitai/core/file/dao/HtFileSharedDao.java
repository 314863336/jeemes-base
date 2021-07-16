package com.huitai.core.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.common.utils.Page;
import com.huitai.core.file.entity.HtFileShared;

/**
 * <p>
 * 文档共享表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-05-28
 */
public interface HtFileSharedDao extends BaseMapper<HtFileShared> {

    /**
     * description: 获取我分享的文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/29 16:46 <br>
     * author: XJM <br>
     */
    Page<HtFileShared> findFormMeList(Page<HtFileShared> page);

}
