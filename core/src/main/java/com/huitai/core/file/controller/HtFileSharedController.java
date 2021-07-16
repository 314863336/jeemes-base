package com.huitai.core.file.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.entity.HtFileShared;
import com.huitai.core.file.service.HtFileInfoService;
import com.huitai.core.file.service.HtFileSharedService;
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
 * description 文档共享表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-05-28 14:41 <br>
 * version: 1.0 <br>
 */

@RestController
@Api(value = "ht_file_sharedCRUD接口")
@RequestMapping("/htFileShared")
public class HtFileSharedController extends BaseController {

	@Autowired
	private HtFileSharedService htFileSharedService;

	@Autowired
	private HtFileInfoService htFileInfoService;

	@Autowired
	private HtSysUserService htSysUserService;


	@ApiOperation(value = "获取ht_file_shared列表",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtFileShared htFileShared) {
		Wrapper<HtFileShared> queryWrapper = new QueryWrapper<>(htFileShared);
		List<HtFileShared> list = htFileSharedService.list(queryWrapper);
		return Result.ok(list);
	}

	@ApiOperation(value = "获取我分享的文件列表",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/fromMeList",method = {RequestMethod.POST})
	public Result fromMeList(@RequestBody Page<HtFileShared> page) {
		HtSysUser htSysUser = UserUtil.getCurUser();
		if(page.getParams() == null){
			page.setParams(new HtFileShared());
		}
		page.getParams().setFromUserId(htSysUser.getId());
		Page<HtFileShared> list = htFileSharedService.findFormMeList(page);
		return Result.ok(list);
	}

	@ApiOperation(value = "获取文件夹下一级列表分页数据",notes="")
	@ApiParam(name = "page", value = "实体")
	@RequestMapping(value="/pageByParentId",method = {RequestMethod.POST})
	public Result pageByParentId(@RequestBody Page<HtFileInfo> page, String rootId) {
		HtFileShared root = htFileSharedService.getById(rootId);

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
		Page<HtFileShared> htFileSharedPage = new Page<>();
		htFileSharedPage.setCurrent(htFileSharedPage.getCurrent());
		htFileSharedPage.setSize(htFileSharedPage.getSize());
		htFileSharedPage.setTotal(htFileSharedPage.getTotal());
		List<HtFileShared> htFileShareds = new ArrayList<>();
		if(htFileInfos != null && htFileInfos.size() > 0){
			HtFileShared htFileShared = null;
			for (HtFileInfo htFileInfo : htFileInfos) {
				htFileShared = new HtFileShared();
				htFileShared.setId(htFileInfo.getId());
				htFileShared.setFromUserId(root.getFromUserId());
				htFileShared.setFileInfoId(htFileInfo.getId());
				htFileShared.setHtFileInfo(htFileInfo);
				htFileShared.setFromUserName(htSysUser.getUserName());
				htFileShared.setCreateDate(root.getCreateDate());
				htFileShareds.add(htFileShared);
			}
		}
		htFileSharedPage.setRecords(htFileShareds);
		Result result = Result.ok(htFileSharedPage);
		result.put("parentId", params.getParentId());
		return result;
	}

	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取详情",notes="")
	@RequestMapping(value="/detail",method = {RequestMethod.GET})
	public Result detail(String id) {
		HtFileShared htFileShared = htFileSharedService.getById(id);
		return Result.ok(htFileShared);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtFileShared htFileShared){
		htFileSharedService.save(htFileShared);
		return Result.ok();
	}

	/**
	 * description: 分享文件 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/29 16:30 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "分享文件",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/shareFile",method = {RequestMethod.POST})
	public Result shareFile(@RequestBody List<HtFileShared> htFileShareds){
		htFileSharedService.shareFile(htFileShareds);
		return Result.ok();
	}


	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htFileShared", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtFileShared htFileShared){
		htFileSharedService.updateById(htFileShared);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
		//物理删除
		htFileSharedService.removeById(id);
		return Result.ok();
	}

	@ApiOperation(value = "删除分享给我的",notes="")
	@RequestMapping(value="/deleteShared",method = {RequestMethod.GET})
	public Result deleteShared(String id){
		htFileSharedService.deleteShared(id);
		return Result.ok();
	}

}
