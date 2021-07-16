package com.huitai.core.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.common.utils.Page;
import com.huitai.core.system.entity.HtSysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-08
 */
public interface HtSysUserService extends IService<HtSysUser> {

    // 用户类型 普通用户
    int USER_TYPE_0 = 0;

    // 用户类型 二级管理员
    int USER_TYPE_1 = 1;

    // 用户类型 系统管理员
    int USER_TYPE_2 = 2;

    // 用户类型 超级管理员
    int USER_TYPE_3 = 3;

    String SYS_USER_INIT_PASSWORD = "sys.user.initPassword";

    /**
     * description: 根据id获取实体对象 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:49 <br>
     * author: XJM <br>
     */
    HtSysUser getUserById(String id);

    /**
     * description: 批量删除用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 10:39 <br>
     * author: XJM <br>
     */
    void deleteBatch(String[] ids);

    /**
     * description: 修改用户状态 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 10:53 <br>
     * author: XJM <br>
     */
    void updateStatus(String id, String status);

    /**
     * description: 修改密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:31 <br>
     * author: XJM <br>
     */
    void updatePassword(String id, String newPassWord);

    /**
     * description: 重置密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:44 <br>
     * author: XJM <br>
     */
    void resetPassword(String id);

    /**
     * description: 根据loginCode获取用户信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 13:37 <br>
     * author: XJM <br>
     */
    HtSysUser getByLoginCode(String loginCode);

    /**
     * description: 获取用户列表数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:22 <br>
     * author: XJM <br>
     */
    Page<HtSysUser> findHtSysUserList(Page<HtSysUser> page);

    /**
     * description: 保存或修改用户信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 17:44 <br>
     * author: XJM <br>
     */
    void saveOrUpdateUser(HtSysUser htSysUser);

    /**
     * description: 根据ids删除用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 10:54 <br>
     * author: XJM <br>
     */
    void deleteUser(String[] ids);

    /**
     * description: 根据查询参数获取用户数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:34 <br>
     * author: TYJ <br>
     */
    List<HtSysUser> listExcel(Map<String, Object> params);

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
    IPage<HtSysUser> pageByPosts(IPage<?> page, String posts, HtSysUser htSysUser);
}
