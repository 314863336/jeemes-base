package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.system.entity.HtSysPostRole;

import java.util.List;

/**
 * <p>
 * 岗位角色表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysPostRoleDao extends BaseMapper<HtSysPostRole> {

    /**
     * description: 根据角色id获取所有关联的岗位id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    List<String> findPostsOfRole(String id);

    /**
     * description: 根据岗位id获取所有关联的角色id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 15:05 <br>
     * author: XJM <br>
     */
    List<String> findRolesOfPost(String id);

}
