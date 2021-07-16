package com.huitai.core.job.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.job.entity.HtSysJob;
import com.huitai.core.job.service.HtSysJobService;
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
 * description 作业调度表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-05-25 17:20 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_sys_jobCRUD接口")
@RequestMapping("/htSysJob")
public class HtSysJobController extends BaseController {

	@Autowired
	private HtSysJobService htSysJobService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_job列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtSysJob> page, @RequestBody HtSysJob htSysJob) {
		QueryWrapper<HtSysJob> queryWrapper = new QueryWrapper<>();
		if(StringUtil.isNotBlank(htSysJob.getJobName())){
			queryWrapper.like("job_name", htSysJob.getJobName());
		}
		if(StringUtil.isNotBlank(htSysJob.getDescription())){
			queryWrapper.like("description", htSysJob.getDescription());
		}
		IPage<HtSysJob> pageList = htSysJobService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_sys_job列表",notes="")
	@ApiParam(name = "htSysJob", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysJob htSysJob) {
		Wrapper<HtSysJob> queryWrapper = new QueryWrapper<>(htSysJob);
		List<HtSysJob> list = htSysJobService.list(queryWrapper);
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
		HtSysJob htSysJob = htSysJobService.getById(id);
		return Result.ok(htSysJob);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysJob", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysJob htSysJob) throws Exception{
		htSysJobService.saveTask(htSysJob);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htSysJob", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtSysJob htSysJob) throws Exception{
		htSysJobService.updateTask(htSysJob);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id) throws Exception{

		//物理删除
		htSysJobService.deleteJob(id);
		return Result.ok();
	}
	
	/**
	 * description: 暂停任务 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/26 10:44 <br>
	 * author: TYJ <br>
	 */ 
	@ApiOperation(value = "暂停任务",notes="")
	@RequestMapping(value="/pause",method = {RequestMethod.GET})
	public Result pause(String id) throws Exception{
		htSysJobService.pause(id);
		return Result.ok();
	}

	/**
	 * description: 恢复任务 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/26 10:56 <br>
	 * author: TYJ <br>
	 */
	@ApiOperation(value = "恢复任务",notes="")
	@RequestMapping(value="/resume",method = {RequestMethod.GET})
	public Result resume(String id) throws Exception{
		htSysJobService.resume(id);
		return Result.ok();
	}

	/**
	 * description: 立即触发一次任务 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/26 11:34 <br>
	 * author: TYJ <br>
	 */
	@ApiOperation(value = "立即触发一次任务",notes="")
	@RequestMapping(value="/trigger",method = {RequestMethod.GET})
	public Result trigger(String id) throws Exception{
		htSysJobService.trigger(id);
		return Result.ok();
	}

}
