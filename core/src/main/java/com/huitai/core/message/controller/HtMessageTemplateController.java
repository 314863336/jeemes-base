package com.huitai.core.message.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.message.entity.HtMessageTemplate;
import com.huitai.core.message.service.HtMessageTemplateService;
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
 * description 消息模板 前端控制器 <br>
 * author XJM <br>
 * date: 2020-04-30 17:28 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_message_templateCRUD接口")
@RequestMapping("/htMessageTemplate")
public class HtMessageTemplateController extends BaseController {

	@Autowired
	private HtMessageTemplateService htMessageTemplateService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_message_template列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(@RequestBody Page<HtMessageTemplate> page) {

		QueryWrapper<HtMessageTemplate> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id", "tpl_title", "tpl_code", "tpl_type", "status", "remarks");
		HtMessageTemplate htMessageTemplate = page.getParams();
		if(htMessageTemplate != null){
			if(StringUtil.isNotBlank(htMessageTemplate.getTplCode()))queryWrapper.like("tpl_code", htMessageTemplate.getTplCode());
			if(StringUtil.isNotBlank(htMessageTemplate.getTplTitle()))queryWrapper.like("tpl_title", htMessageTemplate.getTplTitle());
			if(StringUtil.isNotBlank(htMessageTemplate.getStatus()))queryWrapper.eq("status", htMessageTemplate.getStatus());
		}
		Page<HtMessageTemplate> pageList = htMessageTemplateService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_message_template列表",notes="")
	@ApiParam(name = "htMessageTemplate", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtMessageTemplate htMessageTemplate) {
		Wrapper<HtMessageTemplate> queryWrapper = new QueryWrapper<>(htMessageTemplate);
		List<HtMessageTemplate> list = htMessageTemplateService.list(queryWrapper);
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
		HtMessageTemplate htMessageTemplate = htMessageTemplateService.getById(id);
		return Result.ok(htMessageTemplate);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htMessageTemplate", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtMessageTemplate htMessageTemplate){
		htMessageTemplateService.saveOrUpdateTemplate(htMessageTemplate);
		return Result.ok(htMessageTemplate);
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htMessageTemplate", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtMessageTemplate htMessageTemplate){
		htMessageTemplateService.updateById(htMessageTemplate);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
		htMessageTemplateService.removeById(id);
		return Result.ok();
	}

}
