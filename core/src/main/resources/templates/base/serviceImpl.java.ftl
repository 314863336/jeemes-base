package ${package.ServiceImpl};

import com.huitai.common.utils.StringUtil;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if cfg.hostTable.EntityName==entity>
 <#list cfg.subTables as subTable>
import ${package.Service}.${subTable.EntityName}Service;
import ${package.Entity}.${subTable.EntityName};
 </#list>
</#if>

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    <#if cfg.hostTable.EntityName==entity>
        <#list cfg.subTables as subTable>
    @Autowired
    private ${subTable.EntityName}Service ${subTable.entityName}Service;
        </#list>
    </#if>

    <#if cfg.hostTable.EntityName==entity && cfg.subTables?size gt 0>
    @Transactional
    @Override
    public void saveData(${entity} ${table.entityPath}) {
        super.save(${table.entityPath});
        <#list cfg.subTables as subTable>
        if(${table.entityPath}.get${subTable.EntityName}List() != null && ${table.entityPath}.get${subTable.EntityName}List().size() > 0){
            for(${subTable.EntityName} ${subTable.entityName} : ${table.entityPath}.get${subTable.EntityName}List()){
                ${subTable.entityName}.set${subTable.FkName}(${table.entityPath}.get${subTable.ParentFkName}());
            }
            ${subTable.entityName}Service.saveBatch(${table.entityPath}.get${subTable.EntityName}List());
        }
        </#list>
    }


    @Transactional
    @Override
    public void updateData(${entity} ${table.entityPath}) {
        super.updateById(${table.entityPath});
        <#list cfg.subTables as subTable>
            if(${table.entityPath}.get${subTable.EntityName}List() != null && ${table.entityPath}.get${subTable.EntityName}List().size() > 0){
            for(${subTable.EntityName} ${subTable.entityName} : ${table.entityPath}.get${subTable.EntityName}List()){
                ${subTable.entityName}Service.updateById(${subTable.entityName});
            }
        }
        </#list>
    }

    @Transactional
    @Override
    public void deleteData(String id) {
        ${entity} ${table.entityPath} = getById(id);
        super.removeById(id);
        <#list cfg.subTables as subTable>
        if(${table.entityPath} != null && StringUtil.isNotBlank(${table.entityPath}.get${subTable.ParentFkName}())){
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("${subTable.fk_name}", ${table.entityPath}.get${subTable.ParentFkName}());
            ${subTable.entityName}Service.removeByMap(columnMap);
        }
        </#list>
    }
    </#if>





}
</#if>