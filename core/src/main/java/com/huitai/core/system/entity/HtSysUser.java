package com.huitai.core.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author XJM
 * @since 2020-04-08
 */
@ApiModel(value="HtSysUser对象", description="")
public class HtSysUser extends BaseEntity {

    @NotBlank(message = "登录账号不能为空")
    @Length(max=100, message = "登陆账号长度不能超过100个字符")
    @ExcelProperty(value = "登录账号")
    @ApiModelProperty(value = "登录账号(唯一)")
    private String loginCode;

    @NotBlank(message = "所属公司不能为空")
    @ExcelProperty(value = "公司id")
    @ApiModelProperty(value = "所属公司")
    private String companyId;

    @NotBlank(message = "所属部门不能为空")
    @ExcelProperty(value = "部门id")
    @ApiModelProperty(value = "所属部门")
    private String deptId;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max=100, message = "用户昵称长度不能超过100个字符")
    @ExcelProperty(value = "昵称")
    @ApiModelProperty(value = "昵称")
    private String userName;

    @ExcelIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @Length(max=50, message = "邮箱长度不能超过50个字符")
    @ExcelProperty(value = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Length(max=50, message = "手机号长度不能超过50个字符")
    @ExcelProperty(value = "手机号")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Length(max=50, message = "办公电话长度不能超过50个字符")
    @ExcelProperty(value = "办公电话")
    @ApiModelProperty(value = "办公电话")
    private String phone;

    @ExcelIgnore
    @ApiModelProperty(value = "性别(1男2女)，数据字典设置")
    private String sex;

    @ExcelProperty(value = "性别")
    @ApiModelProperty(value = "性别")
    @TableField(exist = false)
    private String sexName;

    @ExcelIgnore
    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @Length(max=200, message = "个性签名长度不能超过50个字符")
    @ExcelIgnore
    @ApiModelProperty(value = "个性签名")
    private String sign;

    @ExcelIgnore
    @ApiModelProperty(value = "绑定的微信号")
    private String wxOpenid;

    @ExcelIgnore
    @ApiModelProperty(value = "用户类型(数据字典设置，0普通用户，1 二级管理员，2 系统管理员)")
    private Integer userType;

    @ExcelIgnore
    @ApiModelProperty(value = "所属公司")
    @TableField(exist = false)
    private HtSysOffice company;

    @ExcelIgnore
    @ApiModelProperty(value = "所属部门")
    @TableField(exist = false)
    private HtSysOffice dept;

    @ExcelProperty(value = "所属公司")
    @ApiModelProperty(value = "所属公司名称")
    @TableField(exist = false)
    private String companyName;

    @ExcelProperty(value = "所属部门")
    @ApiModelProperty(value = "所属部门名称")
    @TableField(exist = false)
    private String officeName;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @Override
    public String toString() {
        return "HtSysUser{" +
                "id='" + super.getId() + '\'' +
                ", loginCode='" + loginCode + '\'' +
                ", companyId='" + companyId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sign='" + sign + '\'' +
                ", wxOpenid='" + wxOpenid + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public HtSysOffice getCompany() {
        return company;
    }

    public void setCompany(HtSysOffice company) {
        this.company = company;
    }

    public HtSysOffice getDept() {
        return dept;
    }

    public void setDept(HtSysOffice dept) {
        this.dept = dept;
    }
}
