package com.huitai.core.tools.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.core.base.BaseController;
import com.huitai.core.tools.entity.HtGenTable;
import com.huitai.core.tools.service.HtGenTableColumnService;
import com.huitai.core.tools.service.HtGenTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * description 代码生成信息表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-05-20 09:17 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_gen_tableCRUD接口")
@RequestMapping("/htGenTable")
public class HtGenTableController extends BaseController {

	@Autowired
	private HtGenTableService htGenTableService;

	@Autowired
	private HtGenTableColumnService htGenTableColumnService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_gen_table列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtGenTable> page, @RequestBody HtGenTable htGenTable) {
		IPage<HtGenTable> pageList = htGenTableService.pageList(page, htGenTable);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_gen_table列表",notes="")
	@ApiParam(name = "htGenTable", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtGenTable htGenTable) {
		Wrapper<HtGenTable> queryWrapper = new QueryWrapper<>(htGenTable);
		List<HtGenTable> list = htGenTableService.list(queryWrapper);
		return Result.ok(list);
	}
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取详情",notes="")
	@RequestMapping(value="/detail",method = {RequestMethod.GET})
	public Result detail(String id) {
		HtGenTable htGenTable = htGenTableService.getById(id);
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("table_name", htGenTable.getTableName());
		htGenTable.setHtGenTableColumns(htGenTableColumnService.list(queryWrapper));
		return Result.ok(htGenTable);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htGenTable", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtGenTable htGenTable){
		htGenTableService.saveGenTable(htGenTable);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htGenTable", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtGenTable htGenTable){
		htGenTableService.updateGenTable(htGenTable);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
		htGenTableService.deleteGenTable(id);
		return Result.ok();
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除", notes = "")
	@RequestMapping(value = "/batchDeleteGen", method = {RequestMethod.POST})
	public Result batchDeleteGen(@RequestBody String[] ids) {
		if(ids==null){
			return Result.error("未选择要删除的数据项！");
		}
		//物理删除
		htGenTableService.removeByIds(Arrays.asList(ids));
		return Result.ok("删除成功！");
	}

	/**
	 * description: 获取数据库表 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/21 9:15 <br>
	 * author: TYJ <br>
	 */
    @ApiOperation(value = "获取数据库表",notes="")
    @RequestMapping(value="/getTables",method = {RequestMethod.GET})
	public Result getTables(String tableName){
        return Result.ok(htGenTableService.getTables(tableName));
    }

    /**
     * description: 获取表字段信息 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 11:12 <br>
     * author: TYJ <br>
     */
    @ApiOperation(value = "获取表字段信息",notes="")
    @RequestMapping(value="/getColumns",method = {RequestMethod.GET})
	public Result getColumns(String tableName){
        return Result.ok(htGenTableService.getColumns(tableName));
    }

    /**
     * description: 获取可选的父表 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 11:18 <br>
     * author: TYJ <br>
     */
    @ApiOperation(value = "获取可选的父表",notes="")
    @RequestMapping(value="/getParentTables",method = {RequestMethod.GET})
	public Result getParentTables(String excludeName){
        return Result.ok(htGenTableService.getParentTables(excludeName));
    }

	/**
	 * description: 生成代码 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/22 14:59 <br>
	 * author: TYJ <br>
	 */ 
	@ApiOperation(value = "生成代码",notes="")
	@RequestMapping(value="/genCode",method = {RequestMethod.GET})
    public Result genCode(String id){
		htGenTableService.genCode(id);
		return Result.ok();
	}


}
