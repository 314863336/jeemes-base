package com.huitai.core.system.entity;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XJM
 * @since 2020-04-22
 */
@ApiModel(value="HtSysUserPost对象", description="")
public class HtSysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String postId;

    private String userId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "HtSysUserPost{" +
            ", postId=" + postId +
            ", userId=" + userId +
        "}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
