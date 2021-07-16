package com.huitai.core.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.entity.HtFileReceived;
import com.huitai.core.file.service.HtFileInfoService;
import com.huitai.core.file.service.HtFileReceivedService;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysUserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * description 文档共享接收表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-05-29 17:08 <br>
 * version: 1.0 <br>
 */

@RestController
@Api(value = "ht_file_receivedCRUD接口")
@RequestMapping("/htFileReceived")
public class HtFileReceivedController extends BaseController {

	@Autowired
	private HtFileReceivedService htFileReceivedService;

	@Autowired
	private HtSysUserService htSysUserService;

	@Autowired
	private HtFileInfoService htFileInfoService;


	@ApiOperation(value = "获取分享给我的文件列表",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/toMeList",method = {RequestMethod.POST})
	public Result toMeList(@RequestBody Page<HtFileReceived> page) {
		HtSysUser htSysUser = UserUtil.getCurUser();
		if(page.getParams() == null){
			page.setParams(new HtFileReceived());
		}
		page.getParams().setToUserId(htSysUser.getId());
		Page<HtFileReceived> list = htFileReceivedService.findToMeList(page);
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
		HtFileReceived htFileReceived = htFileReceivedService.getById(id);
		return Result.ok(htFileReceived);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htFileReceived", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtFileReceived htFileReceived){
		htFileReceivedService.save(htFileReceived);
		return Result.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htFileReceived", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtFileReceived htFileReceived){
		htFileReceivedService.updateById(htFileReceived);
		return Result.ok();
	}

	@ApiOperation(value = "获取文件夹下一级列表分页数据",notes="")
	@ApiParam(name = "page", value = "实体")
	@RequestMapping(value="/pageByParentId",method = {RequestMethod.POST})
	public Result pageByParentId(@RequestBody Page<HtFileInfo> page, String rootId) {
		HtFileReceived root = htFileReceivedService.getById(rootId);

		HtSysUser htSysUser = htSysUserService.getById(root.getFromUserId());
		HtFileInfo params = page.getParams();
		// 获取上一级数据
		if("-1".equals(params.getParentId())){
			HtFileInfo pHtFileInfo = htFileInfoService.getById(params.getId());
			params.setParentId(pHtFileInfo.getParentId());
		}
		QueryWrapper<HtFileInfo> queryWrapper = new QueryWrapper<>();
		if(StringUtil.isNotBlank(params.getParentId()))queryWrapper.eq("parent_id", params.getParentId());
		if(StringUtil.isNotBlank(params.getFileName()))queryWrapper.like("file_name", params.getFileName());
		Page<HtFileInfo> htFileInfoPage = htFileInfoService.page(page, queryWrapper);
		List<HtFileInfo> htFileInfos = htFileInfoPage.getRecords();
		Page<HtFileReceived> htFileReceivedPage = new Page<>();
		htFileReceivedPage.setCurrent(htFileInfoPage.getCurrent());
		htFileReceivedPage.setSize(htFileInfoPage.getSize());
		htFileReceivedPage.setTotal(htFileInfoPage.getTotal());
		List<HtFileReceived> htFileReceiveds = new ArrayList<>();
		if(htFileInfos != null && htFileInfos.size() > 0){
			HtFileReceived htFileReceived = null;
			for (HtFileInfo htFileInfo : htFileInfos) {
				htFileReceived = new HtFileReceived();
				htFileReceived.setId(htFileInfo.getId());
				htFileReceived.setFromUserId(root.getFromUserId());
				htFileReceived.setFileInfoId(htFileInfo.getId());
				htFileReceived.setHtFileInfo(htFileInfo);
				htFileReceived.setFromUserName(htSysUser.getUserName());
				htFileReceived.setCreateDate(root.getCreateDate());
				htFileReceiveds.add(htFileReceived);
			}
		}
		htFileReceivedPage.setRecords(htFileReceiveds);
		Result result = Result.ok(htFileReceivedPage);
		result.put("parentId", params.getParentId());
		return result;
	}

	/**
	 * description: 删除分享给我的文件或文件夹 <br>
	 * version: 1.0 <br>
	 * date: 2020/6/1 15:17 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/deleteReceived",method = {RequestMethod.GET})
	public Result deleteReceived(String id){
		//物理删除
		htFileReceivedService.removeById(id);
		return Result.ok();
	}
}
