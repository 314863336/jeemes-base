package com.huitai.core.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.exception.SystemException;
import com.huitai.core.system.entity.HtSysConfig;
import com.huitai.core.system.service.HtSysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * description 系统参数表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-04-22 11:15 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_sys_configCRUD接口")
@RequestMapping("/htSysConfig")
public class HtSysConfigController extends BaseController {

	@Autowired
	private HtSysConfigService htSysConfigService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_config列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(@RequestBody Page<HtSysConfig> page) {
		HtSysConfig htSysConfig = page.getParams();
		QueryWrapper<HtSysConfig> queryWrapper = new QueryWrapper<>();
		if(htSysConfig != null){
			if(StringUtils.isNotBlank(htSysConfig.getConfigKey())){
				queryWrapper.like("config_key", htSysConfig.getConfigKey());
			}
			if(StringUtils.isNotBlank(htSysConfig.getConfigName())){
				queryWrapper.like("config_name", htSysConfig.getConfigName());
			}
		}

		Page<HtSysConfig> pageList = htSysConfigService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_sys_config列表",notes="")
	@ApiParam(name = "htSysConfig", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysConfig htSysConfig) {
		Wrapper<HtSysConfig> queryWrapper = new QueryWrapper<>(htSysConfig);
		List<HtSysConfig> list = htSysConfigService.list(queryWrapper);
		return Result.ok(list);
	}

	@ApiOperation(value = "获取ht_sys_config列表(从redis缓存获取)",notes="")
	@ApiParam(name = "htSysConfig", value = "实体")
	@RequestMapping(value="/listRedis",method = {RequestMethod.GET})
	public Result listRedis() {
		List<Serializable> list = htSysConfigService.listRedis();
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
		HtSysConfig htSysConfig = htSysConfigService.getById(id);
		return Result.ok(htSysConfig);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysConfig", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysConfig htSysConfig){
		QueryWrapper<HtSysConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("config_key", htSysConfig.getConfigKey());
		HtSysConfig get = htSysConfigService.getOne(queryWrapper);
		if(get != null){
			throw new SystemException(messageSource.getMessage("system.error.config.dataExist", null, LocaleContextHolder.getLocale()));
		}else{
			htSysConfigService.save(htSysConfig);
		}
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htSysConfig", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtSysConfig htSysConfig){
		htSysConfigService.update(htSysConfig);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
        //逻辑删除
		//HtSysConfig htSysConfig = new HtSysConfig();
		//htSysConfig.setMenuId(id);
		//sysMenu.setDelFlag(-1);
		//htSysConfigService.updateById(htSysConfig);
		//物理删除
		htSysConfigService.removeById(id);
		return Result.ok("删除成功！");
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除",notes="")
	@RequestMapping(value="/batchDelete",method = {RequestMethod.POST})
	public Result batchDelete(@RequestBody String[] ids){
		if(ids==null){
			return Result.error("未选择要删除的数据项！");
		}
		//物理删除
		htSysConfigService.removeByIds(Arrays.asList(ids));
		return Result.ok("删除成功！");
	}

}
