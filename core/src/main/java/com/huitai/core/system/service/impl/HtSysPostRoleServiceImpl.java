package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.system.dao.HtSysPostRoleDao;
import com.huitai.core.system.entity.HtSysPostRole;
import com.huitai.core.system.service.HtSysPostRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 岗位角色表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@Service
public class HtSysPostRoleServiceImpl extends ServiceImpl<HtSysPostRoleDao, HtSysPostRole> implements HtSysPostRoleService {

    /**
     * description: 根据角色id获取所有关联的岗位id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public List<String> findPostsOfRole(String id){
        return baseMapper.findPostsOfRole(id);
    }

    /**
     * description: 分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void assignPost(String[] postIds, String roleId){
        // 先删除后增加
        this.removeHtSysPostRole(roleId);
        // 再重新添加
        HtSysPostRole htSysPostRole = null;
        for (int i = 0; i < postIds.length; i++) {
            if(!StringUtil.isEmpty(postIds[i])){
                htSysPostRole = new HtSysPostRole();
                htSysPostRole.setPostId(postIds[i]);
                htSysPostRole.setRoleId(roleId);
                super.save(htSysPostRole);
            }
        }
    }

    /**
     * description: 根据roleId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:32 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void removeHtSysPostRole(String roleId){
        QueryWrapper<HtSysPostRole> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        super.remove(queryWrapper);
    }

    /**
     * description: 根据岗位id获取所有关联的角色id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public List<String> findRolesOfPost(String id){
        return baseMapper.findRolesOfPost(id);
    }

    /**
     * description: 保存分配角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:09 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void savePostRole(List<String> ids, String postId){
        // 先删除
        QueryWrapper<HtSysPostRole> queryWrapper = new QueryWrapper();
        queryWrapper.eq("post_id", postId);
        super.remove(queryWrapper);
        // 再增加
        HtSysPostRole htSysPostRole = null;
        for (int i = 0; i < ids.size(); i++) {
            htSysPostRole = new HtSysPostRole();
            htSysPostRole.setRoleId(ids.get(i));
            htSysPostRole.setPostId(postId);
            super.save(htSysPostRole);
        }
    }

    /**
     * description: 删除分配角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:50 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deletePostRole(String roleId, String postId){
        QueryWrapper<HtSysPostRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        queryWrapper.eq("role_id", roleId);
        super.remove(queryWrapper);
    }
}
