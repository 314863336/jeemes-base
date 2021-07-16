package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.common.utils.Page;
import com.huitai.core.system.entity.HtSysRole;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
public interface HtSysRoleDao extends BaseMapper<HtSysRole> {

    /**
     * description: 获取角色列表数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:18 <br>
     * author: XJM <br>
     */
    Page<HtSysRole> findHtSysRoleList(Page<HtSysRole> page);

    /**
     * description: 获取用户的所有角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/26 10:10 <br>
     * author: XJM <br>
     */
    List<HtSysRole> findRoleOfUser(String userId);
}
