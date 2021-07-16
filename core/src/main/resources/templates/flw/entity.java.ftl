package ${package.Entity};

import com.alibaba.excel.annotation.ExcelIgnore;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.huitai.bpm.annotaion.FlwAnnotaion;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@FlwAnnotaion("${table.comment!}")
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if swagger2>
@ApiModel(value="${entity}对象", description="${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${entity} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#if field.keyFlag>
        <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.name}")
        </#if>
        <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.name}")
    </#if>
    <#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    <#-- 主键不添加字段校验(前端保存时不传主键参数)，非主键判断是否为空添加校验-->
    <#if !field.keyFlag>
        <#if ("mysql" == cfg.dbType && field.customMap.NULL=="NO") || ("oracle" == cfg.dbType && field.customMap.NULLABLE=="N")>
            <#if field.columnType=="STRING">
    @NotBlank(message="${field.comment}不能为空")
            <#else>
    @NotNull(message="${field.comment}不能为空")
            </#if>
        </#if>
        <#if field.columnType=="STRING" && field.type?index_of("(") != -1>
    @Length(max=${field.type?substring(field.type?index_of("(")+1,field.type?index_of(")"))}, message="${field.comment}长度不能超过${field.type?substring(field.type?index_of("(")+1,field.type?index_of(")"))}个字符")
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

<#------------  子表集合  ------------>
<#if cfg.hostTable.EntityName==entity>
    <#list cfg.subTables as subTable>
    @ApiModelProperty(value = "子表列表")
    @TableField(exist=false)
    @ExcelIgnore
    private List<${subTable.EntityName}> ${subTable.entityName}List;
    </#list>
</#if>

<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
    @Override
    protected Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>

<#if cfg.hostTable.EntityName==entity>
    <#list cfg.subTables as subTable>
    public List<${subTable.EntityName}> get${subTable.EntityName}List() {
        return ${subTable.entityName}List;
    }

    public void set${subTable.EntityName}List(List<${subTable.EntityName}> ${subTable.entityName}List) {
        this.${subTable.entityName}List = ${subTable.entityName}List;
    }
    </#list>
</#if>

<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
