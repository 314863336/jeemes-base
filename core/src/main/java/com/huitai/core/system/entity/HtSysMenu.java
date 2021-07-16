package com.huitai.core.system.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 菜单实体类
 * </p>
 *
 * @author XJM
 * @since 2020-04-10
 */
@ApiModel(value="HtSysMenu对象", description="")
public class HtSysMenu extends BaseTreeEntity {

    @ApiModelProperty(value = "菜单主键")
    private String id;

    @NotBlank(message = "菜单编号不能为空")
    @Length(max=100, message = "菜单编号长度不能超过100个字符")
    @ApiModelProperty(value = "菜单编号(唯一)，与vue中path对应")
    private String menuCode;

    @NotBlank(message = "菜单名称不能为空")
    @Length(max=100, message = "菜单名称长度不能超过100个字符")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @NotBlank(message = "菜单类别不能为空")
    @ApiModelProperty(value = "菜单类别，1菜单2权限")
    private String menuType;

    @ApiModelProperty(value = "菜单链接")
    private String menuHref;

    @NotBlank(message = "菜单打开不能为空")
    @ApiModelProperty(value = "菜单打开方式（1新标签2新浏览器窗口，默认1）")
    private String menuTarget;

    @ApiModelProperty(value = "图标（有默认图标）")
    private String menuIcon;

    @ApiModelProperty(value = "权限标识，菜单类型为权限时必填")
    private String permission;

    @ApiModelProperty(value = "菜单权重（0普通用户，1 二级管理员，2 系统管理员，3超级管理员）")
    private Integer weight;

    @NotBlank(message = "是否显示不能为空")
    @ApiModelProperty(value = "是否显示（1显示 0隐藏）")
    private String isShow;

    @TableField(exist = false)
    @ApiModelProperty(value = "非数据库字段，父菜单对象")
    private HtSysMenu parentMenu;

    @Length(max=20, message = "布局类型长度不能超过20个字符")
    @ApiModelProperty(value = "布局类型")
    private String layoutType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuHref() {
        return menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref;
    }
    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    @Override
    public String toString() {
        return "HtSysMenu{" +
                "id='" + id + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", menuHref='" + menuHref + '\'' +
                ", menuTarget='" + menuTarget + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", permission='" + permission + '\'' +
                ", weight=" + weight +
                ", isShow='" + isShow + '\'' +
                ", parentMenu=" + parentMenu +
                ", layoutType='" + layoutType + '\'' +
                '}';
    }

    @Override
    public void setTreeFields(JSONObject jsonObject){
        super.setTreeFields(jsonObject);
    }

    public HtSysMenu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(HtSysMenu parentMenu) {
        this.parentMenu = parentMenu;
    }
}
