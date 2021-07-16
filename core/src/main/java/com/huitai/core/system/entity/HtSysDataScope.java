package com.huitai.core.system.entity;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <p>
 * 角色数据权限关联表
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@ApiModel(value="HtSysDataScope对象", description="角色数据权限关联表")
public class HtSysDataScope implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleId;

    private String officeId;

    private String type; // 0公司,1部门

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    @Override
    public String toString() {
        return "HtSysDataScope{" +
            ", roleId=" + roleId +
            ", officeId=" + officeId +
        "}";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
