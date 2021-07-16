package com.huitai.core.tools.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.core.base.BaseController;
import com.huitai.core.tools.entity.HtGenTableColumn;
import com.huitai.core.tools.service.HtGenTableColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * description 代码生成字段信息表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-05-20 09:17 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_gen_table_columnCRUD接口")
@RequestMapping("/htGenTableColumn")
public class HtGenTableColumnController extends BaseController {

	@Autowired
	private HtGenTableColumnService htGenTableColumnService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_gen_table_column列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtGenTableColumn> page, @RequestBody HtGenTableColumn htGenTableColumn) {
		Wrapper<HtGenTableColumn> queryWrapper = new QueryWrapper<>(htGenTableColumn);
		IPage<HtGenTableColumn> pageList = htGenTableColumnService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_gen_table_column列表",notes="")
	@ApiParam(name = "htGenTableColumn", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtGenTableColumn htGenTableColumn) {
		Wrapper<HtGenTableColumn> queryWrapper = new QueryWrapper<>(htGenTableColumn);
		List<HtGenTableColumn> list = htGenTableColumnService.list(queryWrapper);
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
		HtGenTableColumn htGenTableColumn = htGenTableColumnService.getById(id);
		return Result.ok(htGenTableColumn);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htGenTableColumn", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtGenTableColumn htGenTableColumn){
		htGenTableColumnService.save(htGenTableColumn);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htGenTableColumn", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtGenTableColumn htGenTableColumn){
		htGenTableColumnService.updateById(htGenTableColumn);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
        //逻辑删除
		//HtGenTableColumn htGenTableColumn = new HtGenTableColumn();
		//htGenTableColumn.setMenuId(id);
		//sysMenu.setDelFlag(-1);
		//htGenTableColumnService.updateById(htGenTableColumn);
		//物理删除
		htGenTableColumnService.removeById(id);
		return Result.ok();
	}

}
