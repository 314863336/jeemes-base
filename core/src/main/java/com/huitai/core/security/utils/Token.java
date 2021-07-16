package com.huitai.core.security.utils;

import com.huitai.core.system.entity.HtSysUser;

import java.io.Serializable;
import java.util.Set;

/**
 * description: Token 存储在redis中的token值 <br>
 * date: 2020/4/23 14:21 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前用户信息，包括公司和部门信息，超级管理员没有公司与部门信息
    private HtSysUser htSysUser;

    // 当前用户拥有的公司数据权限
    private Set<String> companyDataScope;

    // 当前用户拥有的部门数据权限
    private Set<String> deptDataScope;

    // 当前用户拥有的权限字符串
    private String[] permissions;

    public Token(){}

    public HtSysUser getHtSysUser() {
        return htSysUser;
    }

    public void setHtSysUser(HtSysUser htSysUser) {
        this.htSysUser = htSysUser;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public Set<String> getCompanyDataScope() {
        return companyDataScope;
    }

    public void setCompanyDataScope(Set<String> companyDataScope) {
        this.companyDataScope = companyDataScope;
    }

    public Set<String> getDeptDataScope() {
        return deptDataScope;
    }

    public void setDeptDataScope(Set<String> deptDataScope) {
        this.deptDataScope = deptDataScope;
    }
}
