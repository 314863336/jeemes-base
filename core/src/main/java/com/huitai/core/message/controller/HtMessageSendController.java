package com.huitai.core.message.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.message.entity.HtMessageSend;
import com.huitai.core.message.entity.HtMessageTemplate;
import com.huitai.core.message.service.HtMessageReceiveService;
import com.huitai.core.message.service.HtMessageSendService;
import com.huitai.core.message.service.HtMessageTemplateService;
import com.huitai.core.message.utils.MailService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description 消息表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-05-07 10:01 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_message_sendCRUD接口")
@RequestMapping("/htMessageSend")
public class HtMessageSendController extends BaseController {

	@Autowired
	private HtMessageSendService htMessageSendService;

	@Autowired
	private HtMessageReceiveService htMessageReceiveService;

	@Autowired
	private MailService mailService;

	@Autowired
	private HtMessageTemplateService htMessageTemplateService;

	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_message_send列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(@RequestBody Page<HtMessageSend> page) {
		if(page.getParams() == null)page.setParams(new HtMessageSend());
		HtMessageSend htMessageSend = page.getParams();

		HtSysUser htSysUser = UserUtil.getCurUser();
		htMessageSend.setSendUser(htSysUser.getId());

		Page<HtMessageSend> pageList = htMessageSendService.findHtMessageSendList(page);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_message_send列表",notes="")
	@ApiParam(name = "htMessageSend", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtMessageSend htMessageSend) {
		Wrapper<HtMessageSend> queryWrapper = new QueryWrapper<>(htMessageSend);
		List<HtMessageSend> list = htMessageSendService.list(queryWrapper);
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
		HtMessageSend htMessageSend = htMessageSendService.getById(id);
		return Result.ok(htMessageSend);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htMessageSend", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtMessageSend htMessageSend){
		htMessageSendService.save(htMessageSend);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htMessageSend", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtMessageSend htMessageSend){
		htMessageSendService.updateById(htMessageSend);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
		htMessageSendService.removeById(id);
		return Result.ok();
	}


	/**
	 * description: 测试接口 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/11 8:58 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "sendEmail",notes="")
	@RequestMapping(value="/sendEmail",method = {RequestMethod.GET})
	public Result sendEmail(){
		HtMessageTemplate htMessageTemplate = htMessageTemplateService.getById("707525101976813568");
		HtMessageSend htMessageSend = new HtMessageSend();
		htMessageSend.setMsgTitle(htMessageTemplate.getTplTitle());
		htMessageSend.setMsgContent(htMessageTemplate.getTplContent());
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "王八蛋蛋");
		htMessageSend.setMsgContent(map);
		htMessageSend.setReceiveUser("111111111111111111");
		htMessageSend.setSendUser("111111111111111111");
		htMessageSend.setSendDate(new Date());
		htMessageSend.setPushStatus(HtMessageSendService.PUSH_STATUS_1);
		htMessageSend.setMsgType(HtMessageSendService.MSG_TYPE_1);
		htMessageSendService.sendMessage(htMessageSend);

//		HtMessageTemplate htMessageTemplate = htMessageTemplateService.getById("707525101976813568");
//		Map<String, Object> map = new HashMap<>();
//		map.put("userName", "王八蛋蛋");
//		HtMessageSend htMessageSend = new HtMessageSend();
//		htMessageSend.setMsgTitle(htMessageTemplate.getTplTitle());
//		htMessageSend.setMsgContent(htMessageTemplate.getTplContent());
//		htMessageSend.setMessageConverter("239326738@qq.com", map);
//		htMessageSend.setReceiveUser("111111111111111111");
//		htMessageSend.setSendUser("111111111111111111");
//		htMessageSend.setSendDate(new Date());
//		htMessageSend.setPushStatus(HtMessageSendService.PUSH_STATUS_1);
//		htMessageSend.setMsgType(HtMessageSendService.MSG_TYPE_3);
//		htMessageSendService.sendMessage(htMessageSend);

		return Result.ok();
	}

}
