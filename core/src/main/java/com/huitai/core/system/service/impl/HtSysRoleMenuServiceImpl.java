package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.system.dao.HtSysRoleMenuDao;
import com.huitai.core.system.entity.HtSysRoleMenu;
import com.huitai.core.system.service.HtSysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@Service
public class HtSysRoleMenuServiceImpl extends ServiceImpl<HtSysRoleMenuDao, HtSysRoleMenu> implements HtSysRoleMenuService {

    /**
     * description: 根据角色id获取所有关联的菜单id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 14:58 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public List<String> findMenusOfRole(String id){
        return baseMapper.findMenusOfRole(id);
    }

    /**
     * description: 分配菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void assignMenu(String[] menuIds, String roleId){
        // 先删除后增加
        this.removeHtSysRoleMenu(roleId);
        // 再重新添加
        HtSysRoleMenu htSysRoleMenu = null;
        for (int i = 0; i < menuIds.length; i++) {
            if(!StringUtil.isEmpty(menuIds[i])){
                htSysRoleMenu = new HtSysRoleMenu();
                htSysRoleMenu.setMenuId(menuIds[i]);
                htSysRoleMenu.setRoleId(roleId);
                super.save(htSysRoleMenu);
            }
        }
    }


    /**
     * description: 根据roleId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:28 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void removeHtSysRoleMenu(String roleId){
        QueryWrapper<HtSysRoleMenu> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        super.remove(queryWrapper);
    }
}
