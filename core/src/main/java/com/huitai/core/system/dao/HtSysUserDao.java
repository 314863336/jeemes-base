package com.huitai.core.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huitai.common.utils.Page;
import com.huitai.core.system.entity.HtSysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author XJM
 * @since 2020-04-08
 */
public interface HtSysUserDao extends BaseMapper<HtSysUser> {

    /**
     * description: 根据id获取实体对象 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 10:47 <br>
     * author: XJM <br>
     */
    HtSysUser getUserById(String id);

    /**
     * description: 获取用户列表数据，包括机构信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:18 <br>
     * author: XJM <br>
     */
    Page<HtSysUser> findHtSysUserList(Page<HtSysUser> page);

    /**
     * description: 根据查询参数获取用户数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:39 <br>
     * author: TYJ <br>
     */
    List<HtSysUser> selectListByParams(@Param("params") Map<String, Object> params);

    /**
     * @description: 根据岗位分页查询出用户 <br>
     * @param: page 分页对象 <br>
     * @param: posts 岗位编号，多个逗号隔开 <br>
     * @param: htSysUser 筛选条件容器实体 <br>
     * @return: IPage<HtSysUser> 包含分页结果、当前页数、每页大小 <br>
     * @exception: <br>
     * @author: PLF <br>
     * @date: 2021-01-21 16:10 <br>
     */
    IPage<HtSysUser> pageByPosts(IPage<?> page, String[] posts, HtSysUser htSysUser);
}
