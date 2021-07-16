package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.system.dao.HtSysUserPostDao;
import com.huitai.core.system.entity.HtSysUserPost;
import com.huitai.core.system.service.HtSysUserPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@Service
public class HtSysUserPostServiceImpl extends ServiceImpl<HtSysUserPostDao, HtSysUserPost> implements HtSysUserPostService {

    /**
     * description: 根据用户id获取岗位id list <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:09 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public List<Object> findPostsOfUser(String userId){
        QueryWrapper<HtSysUserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("post_id");
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectObjs(queryWrapper);
    }

    /**
     * description: 根据岗位id获取用户id list <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:09 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @DS("slave")
    @Override
    public List<Object> findUsersOfPost(String postId){
        QueryWrapper<HtSysUserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id");
        queryWrapper.eq("post_id", postId);
        return baseMapper.selectObjs(queryWrapper);
    }

    /**
     * description: 删除分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:50 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteUserPost(String userId, String postId){
        QueryWrapper<HtSysUserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        queryWrapper.eq("user_id", userId);
        super.remove(queryWrapper);
    }

    /**
     * description: 分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void assignPost(String[] postIds, String userId){
        // 先删除后增加
        this.removeHtSysUserPost(userId);
        // 再重新添加
        HtSysUserPost htSysUserPost = null;
        for (int i = 0; i < postIds.length; i++) {
            if(!StringUtil.isEmpty(postIds[i])){
                htSysUserPost = new HtSysUserPost();
                htSysUserPost.setPostId(postIds[i]);
                htSysUserPost.setUserId(userId);
                super.save(htSysUserPost);
            }
        }
    }

    /**
     * description: 根据userId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:32 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void removeHtSysUserPost(String userId){
        QueryWrapper<HtSysUserPost> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        super.remove(queryWrapper);
    }

    /**
     * description: 保存分配用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:09 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveUserPost(List<String> userIds, String postId){
        // 先删除
        QueryWrapper<HtSysUserPost> queryWrapper = new QueryWrapper();
        queryWrapper.eq("post_id", postId);
        super.remove(queryWrapper);
        // 再增加
        HtSysUserPost htSysUserPost = null;
        for (int i = 0; i < userIds.size(); i++) {
            htSysUserPost = new HtSysUserPost();
            htSysUserPost.setUserId(userIds.get(i));
            htSysUserPost.setPostId(postId);
            super.save(htSysUserPost);
        }
    }
}
