package com.huitai.core.message.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.message.entity.HtMessageReceive;
import com.huitai.core.message.service.HtMessageReceiveService;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.UserUtil;
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
import java.util.Date;
import java.util.List;

/**
 * description 接收消息表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-05-07 10:02 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_message_receiveCRUD接口")
@RequestMapping("/htMessageReceive")
public class HtMessageReceiveController extends BaseController {

	@Autowired
	private HtMessageReceiveService htMessageReceiveService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_message_receive列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(@RequestBody Page<HtMessageReceive> page) {
		if(page.getParams() == null) page.setParams(new HtMessageReceive());
		HtSysUser htSysUser = UserUtil.getCurUser();
		if(!"1".equals(page.getParams().getType())){//app公告不过滤接收人
			page.getParams().setReceiveUser(htSysUser.getId());
		}
		Page<HtMessageReceive> pageList = htMessageReceiveService.findHtMessageReceiveList(page);

		// 每次前端查询将当前登陆人未读数据数量传递给前端
		QueryWrapper<HtMessageReceive> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("receive_user", htSysUser.getId());
		queryWrapper.eq("read_status", HtMessageReceiveService.READ_STATUS_2);
		Result result = Result.ok(pageList);
		result.put("count", htMessageReceiveService.count(queryWrapper));
		return result;
	}

	@ApiOperation(value = "获取ht_message_receive列表",notes="")
	@ApiParam(name = "htMessageReceive", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtMessageReceive htMessageReceive) {
		Wrapper<HtMessageReceive> queryWrapper = new QueryWrapper<>(htMessageReceive);
		List<HtMessageReceive> list = htMessageReceiveService.list(queryWrapper);
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
		HtMessageReceive htMessageReceive = htMessageReceiveService.getById(id);
		// 修改状态为已读
		htMessageReceive.setReadStatus(HtMessageReceiveService.READ_STATUS_1);
		htMessageReceiveService.updateById(htMessageReceive);
		return Result.ok(htMessageReceive);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htMessageReceive", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtMessageReceive htMessageReceive){
		htMessageReceive.setReceiveDate(new Date());
		HtSysUser htSysUser = UserUtil.getCurUser();
		htMessageReceive.setSendUser(htSysUser.getId());
		htMessageReceiveService.save(htMessageReceive);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htMessageReceive", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtMessageReceive htMessageReceive){
		htMessageReceiveService.updateById(htMessageReceive);
		return Result.ok();
	}
	
	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.POST})
	public Result delete(@RequestBody String[] ids){
		//物理删除
		if(ids==null){
			return Result.error("未选择要删除的数据项！");
		}
		htMessageReceiveService.removeByIds(Arrays.asList(ids));
		return Result.ok();
	}

	/**
	 * description: 获取当前登陆人未读消息数量 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/11 10:04 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "获取当前登陆人未读消息数量",notes="")
	@RequestMapping(value="/getMessageCount",method = {RequestMethod.GET})
	public Result getMessageCount(){
		HtSysUser htSysUser = UserUtil.getCurUser();
		QueryWrapper<HtMessageReceive> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("receive_user", htSysUser.getId());
		queryWrapper.eq("read_status", HtMessageReceiveService.READ_STATUS_2);
		return Result.ok(htMessageReceiveService.count(queryWrapper));
	}

	/**
	 * description: 获取当前登陆人推送消息 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/11 10:04 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "获取当前登陆人推送消息",notes="")
	@RequestMapping(value="/getNewMessage",method = {RequestMethod.GET})
	public Result getNewMessage(){
		return Result.ok(htMessageReceiveService.getNewMessage());
	}


	/**
	 * description: 修改已读状态 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/11 10:03 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "修改已读状态",notes="")
	@ApiParam(name = "changeReadStatus", value = "id数组")
	@RequestMapping(value="/changeReadStatus",method = {RequestMethod.POST})
	public Result changeReadStatus(@RequestBody String[] ids){
		htMessageReceiveService.changeReadStatus(ids);
		return Result.ok();
	}
}
