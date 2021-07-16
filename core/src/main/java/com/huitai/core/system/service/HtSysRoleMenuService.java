package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.system.entity.HtSysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysRoleMenuService extends IService<HtSysRoleMenu> {

    /**
     * description: 根据角色id获取所有关联的菜单id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 14:58 <br>
     * author: XJM <br>
     */
    List<String> findMenusOfRole(String id);

    /**
     * description: 分配菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    void assignMenu(String[] menuIds, String roleId);

    /**
     * description: 根据roleId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:28 <br>
     * author: XJM <br>
     */
    void removeHtSysRoleMenu(String roleId);
}
