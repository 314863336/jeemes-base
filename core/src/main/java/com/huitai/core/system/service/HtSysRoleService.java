package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.common.utils.Page;
import com.huitai.core.system.entity.HtSysDataScope;
import com.huitai.core.system.entity.HtSysRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
public interface HtSysRoleService extends IService<HtSysRole> {

    // 数据权限类别，本部门以及子部门
    String IS_CTRL_DEPT = "0";

    // 数据权限类别，自定义
    String IS_CTRL_CUSTOME = "1";

    // 数据权限类别，本公司以及子公司
    String IS_CTRL_COMPANY = "2";

    /**
     * description: 保存或修改角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 10:11 <br>
     * author: XJM <br>
     */
    void saveOrUpdateHtSysRole(HtSysRole htSysRole);

    /**
     * description: 分配数据权限 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 15:48 <br>
     * author: XJM <br>
     */
    void assignDataScope(List<HtSysDataScope> htSysDataScope, String roleId, String isCtrl);

    /**
     * description: 根据roleIds删除角色 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 16:21 <br>
     * author: XJM <br>
     */
    void deleteRole(String[] ids);

    /**
     * description: 获取角色列表数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 11:22 <br>
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
