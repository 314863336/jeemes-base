package com.huitai.core.tools.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 代码生成信息表
 * </p>
 *
 * @author TYJ
 * @since 2020-05-20
 */
@ApiModel(value="HtGenTable对象", description="代码生成信息表")
public class HtGenTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "表名")
    @Length(max=255, message="表名长度不能超过255个字符")
    private String tableName;

    @ApiModelProperty(value = "本表关联父表的外键名")
    @Length(max=64, message="本表关联父表的外键名长度不能超过64个字符")
    private String tableFkName;

    @ApiModelProperty(value = "关联父表的表名")
    @Length(max=64, message="关联父表的表名长度不能超过64个字符")
    private String parentTableName;

    @ApiModelProperty(value = "父表的外键名")
    @Length(max=64, message="父表的外键名长度不能超过64个字符")
    private String parentTableFkName;

    @ApiModelProperty(value = "使用的模板")
    @Length(max=64, message="使用的模板长度不能超过200个字符")
    private String tplCategory;

    @ApiModelProperty(value = "表说明")
    @Length(max=255, message="表说明长度不能超过255个字符")
    private String comments;

    @ApiModelProperty(value = "生成包路径")
    @Length(max=255, message="生成包路径长度不能超过255个字符")
    private String packageName;

    @ApiModelProperty(value = "生成模块名")
    @Length(max=255, message="生成模块名长度不能超过255个字符")
    private String moduleName;

    @ApiModelProperty(value = "生成功能作者")
    @Length(max=255, message="生成功能作者长度不能超过255个字符")
    private String functionAuthor;

    @ApiModelProperty(value = "生成基础路径")
    @Length(max=255, message="生成基础路径长度不能超过255个字符")
    private String codeBaseDir;

    @ApiModelProperty(value = "备注")
    @Length(max=255, message="备注长度不能超过255个字符")
    private String remarks;

    @ApiModelProperty(value = "创建者")
    @Length(max=64, message="创建者长度不能超过64个字符")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更改者")
    @Length(max=64, message="更改者长度不能超过64个字符")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "子表个数")
    @TableField(exist=false)
    @ExcelIgnore
    private Long subCount;

    @ApiModelProperty(value = "表字段信息")
    @TableField(exist=false)
    @ExcelIgnore
    private List<HtGenTableColumn> htGenTableColumns;


    public List<HtGenTableColumn> getHtGenTableColumns() {
        return htGenTableColumns;
    }

    public void setHtGenTableColumns(List<HtGenTableColumn> htGenTableColumns) {
        this.htGenTableColumns = htGenTableColumns;
    }

    public Long getSubCount() {
        return subCount;
    }

    public void setSubCount(Long subCount) {
        this.subCount = subCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getTableFkName() {
        return tableFkName;
    }

    public void setTableFkName(String tableFkName) {
        this.tableFkName = tableFkName;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }
    public String getParentTableFkName() {
        return parentTableFkName;
    }

    public void setParentTableFkName(String parentTableFkName) {
        this.parentTableFkName = parentTableFkName;
    }


    public String getTplCategory() {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory) {
        this.tplCategory = tplCategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getFunctionAuthor() {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }
    public String getCodeBaseDir() {
        return codeBaseDir;
    }

    public void setCodeBaseDir(String codeBaseDir) {
        this.codeBaseDir = codeBaseDir;
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
        return "HtGenTable{" +
            "id=" + id +
            ", tableName=" + tableName +
            ", parentTableName=" + parentTableName +
            ", parentTableFkName=" + parentTableFkName +
            ", tplCategory=" + tplCategory +
            ", comments=" + comments +
            ", packageName=" + packageName +
            ", moduleName=" + moduleName +
            ", functionAuthor=" + functionAuthor +
            ", codeBaseDir=" + codeBaseDir +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }
}
