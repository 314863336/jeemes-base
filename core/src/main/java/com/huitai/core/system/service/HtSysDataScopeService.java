package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.system.entity.HtSysDataScope;

import java.util.List;

/**
 * <p>
 * 角色数据权限关联表 服务类
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
public interface HtSysDataScopeService extends IService<HtSysDataScope> {

    // 数据权限类别，公司
    String DATA_SCOPE_COMAPNY = "0";

    // 数据权限类别，部门
    String DATA_SCOPE_DEPT = "1";

    /**
     * description: 分配数据权限 <br>
     * version: 1.0 <br>
     * date: 2020/4/21 15:40 <br>
     * author: XJM <br>
     */
    void assignDataScope(List<HtSysDataScope> htSysDataScope, String roleId);

    /**
     * description: 根据角色id获取所有关联的数据权限id <br>
     * version: 1.0 <br>
     * date: 2020/4/21 16:04 <br>
     * author: XJM <br>
     */
    List<String> findDataScopesOfRole(String id);
}
