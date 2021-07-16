package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseException;
import com.huitai.core.base.BaseTreeService;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.dao.HtSysMenuDao;
import com.huitai.core.system.entity.HtSysMenu;
import com.huitai.core.system.entity.HtSysRoleMenu;
import com.huitai.core.system.service.HtSysMenuService;
import com.huitai.core.system.service.HtSysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-10
 */
@Service
public class HtSysMenuServiceImpl extends BaseTreeService<HtSysMenuDao, HtSysMenu> implements HtSysMenuService {

    @Autowired
    private HtSysRoleMenuService htSysRoleMenuService;

    /**
     * description: 修改菜单数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 9:05 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    public void saveOrUpdateHtSysMenu(HtSysMenu htSysMenu) {
        // 判断子菜单权重不可以小于父菜单权重
        if(!SystemConstant.DEFAULT_PARENTID.equals(htSysMenu.getParentId())){
            HtSysMenu pHtSysMenu = super.getById(htSysMenu.getParentId());
            if(pHtSysMenu != null){
                if(htSysMenu.getWeight() < pHtSysMenu.getWeight()){
                    throw new BaseException(messageSource.getMessage("system.error.menuWeightException", null, LocaleContextHolder.getLocale()));
                }
            }

        }
        // 验证编号是否存在
        checkExistsField(htSysMenu, "menu_code", htSysMenu.getMenuCode(), messageSource.getMessage("system.error.existsMenuCode", null, LocaleContextHolder.getLocale()));
        htSysMenu.setStatus(SystemConstant.ENABLE);
        super.saveOrUpdateForTreeFields(htSysMenu);
    }

    /**
     * description: 通过id获取菜单对象,包括父菜单信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 10:52 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public HtSysMenu getMenuById(String id) {
        return baseMapper.getMenuById(id);
    }


    /**
     * description: 保存列表排序 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 16:45 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveTreeSortData(List<HtSysMenu> htSysMenuList) {
        List<String> ids = new ArrayList<>();
        List<Integer> treeSorts = new ArrayList<>();
        for (int i = 0; i < htSysMenuList.size(); i++) {
            ids.add(htSysMenuList.get(i).getId());
            treeSorts.add(htSysMenuList.get(i).getTreeSort());
        }
        List<HtSysMenu> list = baseMapper.selectBatchIds(ids);
        for (int i = 0; i < list.size(); i++) {
            if (ids.contains(list.get(i).getId())) {
                list.get(i).setTreeSort(treeSorts.get(ids.indexOf(list.get(i).getId())));
                baseMapper.updateById(list.get(i));
            }
        }
    }

    /**
     * description: 删除菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 17:15 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteMenu(String[] ids){
        QueryWrapper<HtSysRoleMenu> queryWrapperM = null;
        for (int i = 0; i < ids.length; i++) {
            if(!StringUtil.isEmpty(ids[i])){
                super.deleteForTreeFields(ids[i], true);
                // 删除角色菜单关联表数据
                queryWrapperM = new QueryWrapper();
                queryWrapperM.eq("role_id", ids[i]);
                htSysRoleMenuService.remove(queryWrapperM);
            }
        }
    }

    /**
     * description: 根据用户id获取该用户所有的权限字符串 br>
     * version: 1.0 <br>
     * date: 2020/4/23 14:10 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public List<HtSysMenu> findPermissionsOfUser(String userId){
        return baseMapper.findPermissionsOfUser(userId);
    }

    /**
     * description: 根据用户获取用户所有的菜单 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 11:01 <br>
     * author: XJM <br>
     */
    @DS("slave")
    @Transactional(readOnly = true)
    @Override
    public List<HtSysMenu> findUserMenuList(Map<String, Object> params){
        return baseMapper.findUserMenuList(params);
    }
}
