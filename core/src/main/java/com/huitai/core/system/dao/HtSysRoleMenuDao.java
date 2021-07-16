package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.system.entity.HtSysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysRoleMenuDao extends BaseMapper<HtSysRoleMenu> {

    /**
     * description: 根据角色id获取所有关联的菜单id<br>
     * version: 1.0 <br>
     * date: 2020/4/20 14:58 <br>
     * author: XJM <br>
     */
    List<String> findMenusOfRole(String id);
}
