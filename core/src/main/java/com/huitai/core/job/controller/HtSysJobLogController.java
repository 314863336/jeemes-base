package com.huitai.core.job.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.core.base.BaseController;
import com.huitai.core.job.entity.HtSysJobLog;
import com.huitai.core.job.service.HtSysJobLogService;
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
 * description 作业调度日志表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-05-29 15:19 <br>
 * version: 1.0 <br>
 */

@RestController
@Api(value = "ht_sys_job_logCRUD接口")
@RequestMapping("/htSysJobLog")
public class HtSysJobLogController extends BaseController {

	@Autowired
	private HtSysJobLogService htSysJobLogService;


	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_job_log列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtSysJobLog> page, @RequestBody HtSysJobLog htSysJobLog) {
		QueryWrapper<HtSysJobLog> queryWrapper = new QueryWrapper<>(htSysJobLog);
		queryWrapper.orderByDesc("create_date");
		IPage<HtSysJobLog> pageList = htSysJobLogService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_sys_job_log列表",notes="")
	@ApiParam(name = "htSysJobLog", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysJobLog htSysJobLog) {
		Wrapper<HtSysJobLog> queryWrapper = new QueryWrapper<>(htSysJobLog);
		List<HtSysJobLog> list = htSysJobLogService.list(queryWrapper);
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
		HtSysJobLog htSysJobLog = htSysJobLogService.getById(id);
		return Result.ok(htSysJobLog);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysJobLog", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysJobLog htSysJobLog){
		htSysJobLogService.save(htSysJobLog);
		return Result.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htSysJobLog", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtSysJobLog htSysJobLog){
		htSysJobLogService.updateById(htSysJobLog);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
		htSysJobLogService.removeById(id);
		return Result.ok();
	}

}
