package com.huitai.core.tools.entity;

import com.huitai.core.base.BaseEntityD;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 代码生成字段信息表
 * </p>
 *
 * @author TYJ
 * @since 2020-05-20
 */
@ApiModel(value="HtGenTableColumn对象", description="代码生成字段信息表")
public class HtGenTableColumn extends BaseEntityD implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "表名")
    @Length(max=255, message="表名长度不能超过255个字符")
    private String tableName;

    @ApiModelProperty(value = "字段名")
    @Length(max=64, message="字段名长度不能超过64个字符")
    private String columnName;

    @ApiModelProperty(value = "字段类型")
    @Length(max=100, message="字段类型长度不能超过100个字符")
    private String columnType;

    @ApiModelProperty(value = "字段标签名")
    @Length(max=255, message="字段标签名长度不能超过255个字符")
    private String columnLabel;

    @ApiModelProperty(value = "字段备注说明")
    @Length(max=255, message="字段备注说明长度不能超过255个字符")
    private String comments;

    @ApiModelProperty(value = "类的属性名")
    @Length(max=255, message="类的属性名长度不能超过255个字符")
    private String attrName;

    @ApiModelProperty(value = "类的属性类型")
    @Length(max=255, message="类的属性类型长度不能超过255个字符")
    private String attrType;

    @ApiModelProperty(value = "是否主键")
    @Length(max=1, message="是否主键长度不能超过1个字符")
    private String isPk;

    @ApiModelProperty(value = "是否为空")
    @Length(max=1, message="是否为空长度不能超过1个字符")
    private String isNull;

    @ApiModelProperty(value = "是否编辑字段")
    @Length(max=1, message="是否编辑字段长度不能超过1个字符")
    private String isEdit;

    @ApiModelProperty(value = "表单类型")
    @Length(max=255, message="表单类型长度不能超过255个字符")
    private String showType;

    @ApiModelProperty(value = "其他生成选项")
    @Length(max=1000, message="其他生成选项长度不能超过1000个字符")
    private String options;

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
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }
    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }
    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }
    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "HtGenTableColumn{" +
            "id=" + id +
            ", tableName=" + tableName +
            ", columnName=" + columnName +
            ", columnType=" + columnType +
            ", columnLabel=" + columnLabel +
            ", comments=" + comments +
            ", attrName=" + attrName +
            ", attrType=" + attrType +
            ", isPk=" + isPk +
            ", isNull=" + isNull +
            ", isEdit=" + isEdit +
            ", showType=" + showType +
            ", options=" + options +
        "}";
    }
}
