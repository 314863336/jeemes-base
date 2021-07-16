package com.huitai.core.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author TYJ
 * @since 2020-04-15
 */
@ApiModel(value="HtSysOffice对象", description="公司和部门实体")
public class HtSysOffice extends BaseTreeEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构主键")
    private String id;

    @ApiModelProperty(value = "机构编号")
    @NotBlank(message = "编号不能为空")
    @Length(max=100, message = "编号长度不能超过100个字符")
    private String officeCode;

    @ApiModelProperty(value = "所属公司")
    private String companyId;

    @ApiModelProperty(value = "机构名称")
    @NotBlank(message = "名称不能为空")
    @Length(max=100, message = "名称长度不能超过100个字符")
    private String officeName;

    @ApiModelProperty(value = "机构全称")
    @Length(max=200, message = "全称长度不能超过200个字符")
    @NotBlank(message = "全称不能为空")
    private String fullName;

    @ApiModelProperty(value = "机构类型，数据字典设置(0公司，1部门)")
    private String officeType;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "办公电话")
    private String phone;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String zipCode;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "排除id")
    @TableField(exist=false)
    private String[] excludeIds;

    @TableField(exist=false)
    private String parentName;

    @TableField(exist=false)
    private String companyName;

    // 自定义参数
    @TableField(exist=false)
    private Map<String, Object> params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String[] getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(String[] excludeIds) {
        this.excludeIds = excludeIds;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "HtSysOffice{" +
            "id=" + id +
            ", officeCode=" + officeCode +
            ", companyId=" + companyId +
            ", officeName=" + officeName +
            ", fullName=" + fullName +
            ", officeType=" + officeType +
            ", leader=" + leader +
            ", phone=" + phone +
            ", address=" + address +
            ", zipCode=" + zipCode +
            ", email=" + email +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
