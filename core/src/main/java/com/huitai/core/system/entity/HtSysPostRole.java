package com.huitai.core.system.entity;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <p>
 * 岗位角色表
 * </p>
 *
 * @author XJM
 * @since 2020-04-20
 */
@ApiModel(value="HtSysPostRole对象", description="岗位角色表")
public class HtSysPostRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String postId;

    private String roleId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "HtSysPostRole{" +
            ", postId=" + postId +
            ", roleId=" + roleId +
        "}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
