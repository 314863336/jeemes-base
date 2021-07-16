package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.system.entity.HtSysUserPost;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysUserPostService extends IService<HtSysUserPost> {

    /**
     * description: 根据用户id获取岗位id list <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:09 <br>
     * author: XJM <br>
     */
    List<Object> findPostsOfUser(String userId);

    /**
     * description: 分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    void assignPost(String[] postIds, String userId);

    /**
     * description: 根据userId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:32 <br>
     * author: XJM <br>
     */
    void removeHtSysUserPost(String userId);

    /**
     * description: 保存分配用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:09 <br>
     * author: XJM <br>
     */
    void saveUserPost(List<String> ids, String postId);

    /**
     * description: 根据岗位id获取用户id list <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:09 <br>
     * author: XJM <br>
     */
    List<Object> findUsersOfPost(String postId);

    /**
     * description: 删除分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:50 <br>
     * author: XJM <br>
     */
    void deleteUserPost(String userId, String postId);


}
