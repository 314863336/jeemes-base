package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.dao.HtSysRoleDao;
import com.huitai.core.system.entity.HtSysDataScope;
import com.huitai.core.system.entity.HtSysPostRole;
import com.huitai.core.system.entity.HtSysRole;
import com.huitai.core.system.entity.HtSysRoleMenu;
import com.huitai.core.system.service.HtSysDataScopeService;
import com.huitai.core.system.service.HtSysPostRoleService;
import com.huitai.core.system.service.HtSysRoleMenuService;
import com.huitai.core.system.service.HtSysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
@Service
public class HtSysRoleServiceImpl extends BaseServiceImpl<HtSysRoleDao, HtSysRole> implements HtSysRoleService {

    @Autowired
    private HtSysDataScopeService htSysDataScopeService;

    @Autowired
    private HtSysPostRoleService htSysPostRoleService;

    @Autowired
    private HtSysRoleMenuService htSysRoleMenuService;

    /**
     * description: 保存或修改角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 10:11 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateHtSysRole(HtSysRole htSysRole) {
        // 验证编码是否存在
        checkExistsField(htSysRole, "role_code", htSysRole.getRoleCode(), messageSource.getMessage("system.error.existsRoleCode", null, LocaleContextHolder.getLocale()));
        if(StringUtil.isEmpty(htSysRole.getId())){
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.select("max(role_sort) as roleSort");
            HtSysRole t = baseMapper.selectOne(queryWrapper);
            if(t != null){
                htSysRole.setRoleSort(t.getRoleSort() + SystemConstant.DEFAULT_TREE_SORT_ADD);
            }else{
                htSysRole.setRoleSort(SystemConstant.DEFAULT_TREE_SORT);
            }
            htSysRole.setStatus(SystemConstant.ENABLE);
            htSysRole.setIsCtrl(HtSysRoleService.IS_CTRL_DEPT);
            super.save(htSysRole);
        }else{
            super.updateById(htSysRole);
        }
    }

    /**
     * description: 分配数据权限 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 15:48 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void assignDataScope(List<HtSysDataScope> htSysDataScope, String roleId, String isCtrl){
        htSysDataScopeService.assignDataScope(htSysDataScope, roleId);
        HtSysRole htSysRole = baseMapper.selectById(roleId);
        htSysRole.setIsCtrl(isCtrl);
        baseMapper.updateById(htSysRole);
    }

    /**
     * description: 根据roleIds删除角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 16:21 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteRole(String[] ids) {
        QueryWrapper<HtSysPostRole> queryWrapper = null;
        QueryWrapper<HtSysRoleMenu> queryWrapperM = null;
        QueryWrapper<HtSysDataScope> queryWrapperD = null;
        for (int i = 0; i < ids.length; i++) {
            if(!StringUtil.isEmpty(ids[i])) {
                baseMapper.deleteById(ids[i]);
                // 删除角色岗位关联表数据
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("role_id", ids[i]);
                htSysPostRoleService.remove(queryWrapper);
                // 删除角色菜单关联表数据
                queryWrapperM = new QueryWrapper();
                queryWrapperM.eq("role_id", ids[i]);
                htSysRoleMenuService.remove(queryWrapperM);

                // 删除数据权限关联表数据
                queryWrapperD = new QueryWrapper();
                queryWrapperD.eq("role_id", ids[i]);
                htSysDataScopeService.remove(queryWrapperD);
            }
        }
    }

    /**
     * description: 获取角色列表数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:22 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public Page<HtSysRole> findHtSysRoleList(Page<HtSysRole> page){
        if (page.getParams() == null) {
            page.setParams(new HtSysRole());
        }
        if (page.getExtraParams() == null) {
            page.setExtraParams(new HashMap<>());
        }

        return baseMapper.findHtSysRoleList(page);
    }

    /**
     * description: 获取用户的所有角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/26 10:10 <br>
     * author: XJM <br>
     */
    @Override
    public List<HtSysRole> findRoleOfUser(String userId){
        return baseMapper.findRoleOfUser(userId);
    }
}
