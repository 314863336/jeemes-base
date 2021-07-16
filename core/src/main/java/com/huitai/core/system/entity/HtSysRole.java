package com.huitai.core.system.entity;

import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
@ApiModel(value="HtSysRole对象", description="角色表")
public class HtSysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "角色编码不能为空")
    @Length(max=100, message = "角色编码长度不能超过100个字符")
    private String roleCode;

    @NotBlank(message = "角色名称不能为空")
    @Length(max=100, message = "角色名称长度不能超过100个字符")
    private String roleName;

    private Integer roleType;

    private Integer roleSort;

    // 0 本部门以及子部门，1 自定义，2 本公司以及子公司
    private String isCtrl;

    private String status;

    private String remarks;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }
    public String getIsCtrl() {
        return isCtrl;
    }

    public void setIsCtrl(String isCtrl) {
        this.isCtrl = isCtrl;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "HtSysRole{" +
            "id=" + id +
            ", roleCode=" + roleCode +
            ", roleName=" + roleName +
            ", roleType=" + roleType +
            ", roleSort=" + roleSort +
            ", isCtrl=" + isCtrl +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }
}
