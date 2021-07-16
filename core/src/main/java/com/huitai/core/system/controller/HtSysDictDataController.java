package com.huitai.core.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.global.SystemConstant;
import com.huitai.common.model.Result;
import com.huitai.core.system.entity.HtSysDictData;
import com.huitai.core.system.service.HtSysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * description 数据字典子表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-04-20 15:02 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_sys_dict_dataCRUD接口")
@RequestMapping("/htSysDictData")
public class HtSysDictDataController extends BaseController {

	@Autowired
	private HtSysDictDataService htSysDictDataService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_dict_data列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtSysDictData> page) {
		HtSysDictData htSysDictData = new HtSysDictData();
		Wrapper<HtSysDictData> queryWrapper = new QueryWrapper<HtSysDictData>(htSysDictData);
		IPage<HtSysDictData> pageList = htSysDictDataService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_sys_dict_data列表",notes="")
	@ApiParam(name = "htSysDictData", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysDictData htSysDictData) {
		QueryWrapper<HtSysDictData> queryWrapper = new QueryWrapper<>();
		if(htSysDictData!=null){
			if(StringUtils.isNotBlank(htSysDictData.getDictType())){
				queryWrapper.eq("dict_type", htSysDictData.getDictType());
			}
			if(StringUtils.isNotBlank(htSysDictData.getDictLabel())){
				queryWrapper.like("dict_label", htSysDictData.getDictLabel());
			}
			if(StringUtils.isNotBlank(htSysDictData.getDictValue())){
				queryWrapper.eq("dict_value", htSysDictData.getDictValue());
			}
			if(StringUtils.isNotBlank(htSysDictData.getStatus())){
				queryWrapper.eq("status", htSysDictData.getStatus());
			}
		}
		List<HtSysDictData> list = htSysDictDataService.list(queryWrapper);
		return Result.ok(list);
	}

	@ApiOperation(value = "获取ht_sys_dict_data列表(从redis缓存获取)",notes="")
	@ApiParam(name = "htSysDictData", value = "实体")
	@RequestMapping(value="/listRedis",method = {RequestMethod.GET})
	public Result listRedis() {
		List<Serializable> list = htSysDictDataService.listRedis();
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
		HtSysDictData htSysDictData = htSysDictDataService.getById(id);
		return Result.ok(htSysDictData);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysDictData", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@RequestBody HtSysDictData htSysDictData){
		htSysDictDataService.save(htSysDictData);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htSysDictData", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@RequestBody HtSysDictData htSysDictData){
		htSysDictDataService.updateById(htSysDictData);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
        //逻辑删除
		//HtSysDictData htSysDictData = new HtSysDictData();
		//htSysDictData.setMenuId(id);
		//sysMenu.setDelFlag(-1);
		//htSysDictDataService.updateById(htSysDictData);
		//物理删除
		htSysDictDataService.removeById(id);
		return Result.ok();
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除", notes = "")
	@RequestMapping(value = "/batchDeleteData", method = {RequestMethod.POST})
	public Result batchDeleteData(@RequestBody String[] ids) {
		if(ids==null){
			return Result.error("未选择要删除的数据项！");
		}
		//物理删除
		htSysDictDataService.removeByIds(Arrays.asList(ids));
		return Result.ok("删除成功！");
	}

	/**
	 * 启用
	 */
	@ApiOperation(value = "启用",notes="")
	@RequestMapping(value="/enable",method = {RequestMethod.GET})
	public Result enable(String id){
		UpdateWrapper<HtSysDictData> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("status", SystemConstant.ENABLE);
		updateWrapper.eq("id", id);
		htSysDictDataService.update(updateWrapper);
		return Result.ok();
	}

	/**
	 * 停用
	 */
	@ApiOperation(value = "停用",notes="")
	@RequestMapping(value="/disable",method = {RequestMethod.GET})
	public Result disable(String id){
		UpdateWrapper<HtSysDictData> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("status", SystemConstant.DISABLE);
		updateWrapper.eq("id", id);
		htSysDictDataService.update(updateWrapper);
		return Result.ok();
	}

}
