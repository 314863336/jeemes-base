package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.system.entity.HtSysPostRole;

import java.util.List;

/**
 * <p>
 * 岗位角色表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysPostRoleService extends IService<HtSysPostRole> {

    /**
     * description: 根据角色id获取所有关联的岗位id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    List<String> findPostsOfRole(String id);

    /**
     * description: 分配岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:10 <br>
     * author: XJM <br>
     */
    void assignPost(String[] postIds, String roleId);

    /**
     * description: 根据roleId删除关联表 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:32 <br>
     * author: XJM <br>
     */
    void removeHtSysPostRole(String roleId);

    /**
     * description: 根据岗位id获取所有关联的角色id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    List<String> findRolesOfPost(String id);

    /**
     * description: 保存分配角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:09 <br>
     * author: XJM <br>
     */
    void savePostRole(List<String> ids, String postId);

    /**
     * description: 删除分配角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 15:50 <br>
     * author: XJM <br>
     */
    void deletePostRole(String roleId, String postId);

}
