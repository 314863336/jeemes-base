package com.huitai.core.system.service;

import com.huitai.core.base.IBaseTreeService;
import com.huitai.core.system.entity.HtSysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-10
 */
public interface HtSysMenuService extends IBaseTreeService<HtSysMenu> {

    /**
     * description: 修改菜单数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 9:05 <br>
     * author: XJM <br>
     */
    void saveOrUpdateHtSysMenu(HtSysMenu htSysMenu);

    /**
     * description: 通过id获取菜单对象,包括父菜单信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 10:52 <br>
     * author: XJM <br>
     */
    HtSysMenu getMenuById(String id);

    /**
     * description: 保存列表排序 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 16:45 <br>
     * author: XJM <br>
     */
    void saveTreeSortData(List<HtSysMenu> list);

    /**
     * description: 删除菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 17:15 <br>
     * author: XJM <br>
     */
    void deleteMenu(String[] ids);

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
