package com.huitai.core.system.entity;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@ApiModel(value="HtSysRoleMenu对象", description="角色菜单关联表")
public class HtSysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleId;

    private String menuId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "HtSysRoleMenu{" +
            ", roleId=" + roleId +
            ", menuId=" + menuId +
        "}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
