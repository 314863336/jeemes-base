package com.huitai.core.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 岗位表
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
@ApiModel(value="HtSysPost对象", description="岗位表")
public class HtSysPost extends BaseTreeEntity {

    private String id;

    @NotBlank(message = "岗位编码不能为空")
    @Length(max=100, message = "岗位编码长度不能超过100个字符")
    private String postCode;

    @NotBlank(message = "岗位名称不能为空")
    @Length(max=100, message = "岗位名称长度不能超过100个字符")
    private String postName;

    private Integer postType;

    @TableField(exist = false)
    private HtSysPost parentPost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    @Override
    public String toString() {
        return "HtSysPost{" +
            "id=" + id +
            ", postCode=" + postCode +
            ", postName=" + postName +
            ", postType=" + postType +
        "}";
    }

    public HtSysPost getParentPost() {
        return parentPost;
    }

    public void setParentPost(HtSysPost parentPost) {
        this.parentPost = parentPost;
    }
}
