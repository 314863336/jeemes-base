package com.huitai.core.system.dao;

import com.huitai.core.system.entity.HtSysPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 岗位表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
public interface HtSysPostDao extends BaseMapper<HtSysPost> {

    /**
     * description: 通过id获取岗位对象,包括父岗位信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 9:30 <br>
     * author: XJM <br>
     */
    HtSysPost getPostById(String id);
}
