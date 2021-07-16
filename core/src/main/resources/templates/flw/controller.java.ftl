package ${package.Controller};

<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if cfg.hostTable.EntityName==entity>
	<#list cfg.subTables as subTable>
import ${package.Service}.${subTable.EntityName}Service;
	</#list>
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import com.huitai.common.model.Result;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * description ${table.comment!} 前端控制器 <br>
 * author ${author} <br>
 * date: ${cfg.time} <br>
 * version: 1.0 <br>
 */

<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#if swagger2>
@Api(value = "${table.name}CRUD接口")
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

	@Autowired
	private ${table.serviceName} ${table.entityPath}Service;

<#if cfg.hostTable.EntityName==entity>
	<#list cfg.subTables as subTable>
	@Autowired
	private ${subTable.EntityName}Service ${subTable.entityName}Service;
	</#list>
</#if>

	@PostMapping("start")
	public Result start(${entity} ${table.entityPath}, @RequestBody Map<String, Object> params) {
		${table.entityPath}Service.startProcess(${table.entityPath}, params);
		return Result.ok();
	}

	/**
	 * 分页
	 */
	<#if swagger2>
	@ApiOperation(value = "分页获取${table.name}列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	</#if>
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<${entity}> page, @RequestBody ${entity} ${table.entityPath}) {
		Wrapper<${entity}> queryWrapper = new QueryWrapper<>(${table.entityPath});
		IPage<${entity}> pageList = ${table.entityPath}Service.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	<#if swagger2>
	@ApiOperation(value = "获取${table.name}列表",notes="")
	@ApiParam(name = "${table.entityPath}", value = "实体")
	</#if>
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody ${entity} ${table.entityPath}) {
		Wrapper<${entity}> queryWrapper = new QueryWrapper<>(${table.entityPath});
		List<${entity}> list = ${table.entityPath}Service.list(queryWrapper);
		return Result.ok(list);
	}

	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	<#if swagger2>
	@ApiOperation(value = "获取详情",notes="")
	</#if>
	@RequestMapping(value="/detail",method = {RequestMethod.GET})
	public Result detail(String id) {
		${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
		<#if cfg.hostTable.EntityName==entity>
    		<#list cfg.subTables as subTable>
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("${subTable.fk_name}", ${table.entityPath}.get${subTable.ParentFkName}());
		${table.entityPath}.set${subTable.EntityName}List(${subTable.entityName}Service.list(queryWrapper));
			</#list>
		</#if>
		return Result.ok(${table.entityPath});
	}

	/**
	 * 保存
	 */
	<#if swagger2>
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "${table.entityPath}", value = "实体")
	</#if>
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody ${entity} ${table.entityPath}){
	<#if cfg.hostTable.EntityName==entity && cfg.subTables?size gt 0>
		${table.entityPath}Service.saveData(${table.entityPath});
	<#else>
		${table.entityPath}Service.save(${table.entityPath});
	</#if>
		return Result.ok();
	}

	/**
	 * 修改
	 */
	<#if swagger2>
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "${table.entityPath}", value = "实体")
	</#if>
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody ${entity} ${table.entityPath}){
	<#if cfg.hostTable.EntityName==entity && cfg.subTables?size gt 0>
		${table.entityPath}Service.updateData(${table.entityPath});
	<#else>
		${table.entityPath}Service.updateById(${table.entityPath});
	</#if>
		return Result.ok();
	}

	/**
	 * 删除
	 */
	<#if swagger2>
	@ApiOperation(value = "删除",notes="")
	</#if>
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
	<#if cfg.hostTable.EntityName==entity && cfg.subTables?size gt 0>
		${table.entityPath}Service.deleteData(id);
	<#else>
		${table.entityPath}Service.removeById(id);
	</#if>
		return Result.ok();
	}

}
</#if>
