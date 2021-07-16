package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.system.entity.HtSysDataScope;

import java.util.List;

/**
 * <p>
 * 角色数据权限关联表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysDataScopeDao extends BaseMapper<HtSysDataScope> {
    /**
     * description: 根据角色id获取所有关联的数据权限id <br>
     * version: 1.0 <br>
     * date: 2020/4/21 16:04 <br>
     * author: XJM <br>
     */
    List<String> findDataScopesOfRole(String id);
}
