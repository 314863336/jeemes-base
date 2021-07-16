package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.system.entity.HtSysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-10
 */
public interface HtSysMenuDao extends BaseMapper<HtSysMenu> {
    /**
     * description: 通过id获取菜单对象,包括父菜单信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 10:52 <br>
     * author: XJM <br>
     */
    HtSysMenu getMenuById(String id);

    /**
     * description: 根据用户id获取该用户所有的权限字符串 br>
     * version: 1.0 <br>
     * date: 2020/4/23 14:10 <br>
     * author: XJM <br>
     */
    List<HtSysMenu> findPermissionsOfUser(String userId);

    /**
     * description: 根据用户获取用户所有的菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 11:01 <br>
     * author: XJM <br>
     */
    List<HtSysMenu> findUserMenuList(Map<String, Object> params);
}
