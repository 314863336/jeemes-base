package com.huitai.core.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.common.utils.HttpClientUtils;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.base.BaseException;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.service.HtFileInfoService;
import com.huitai.core.global.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * description 文档信息表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-04-30 14:40 <br>
 * version: 1.0 <br>
 */



@RestController
@Api(value = "ht_file_infoCRUD接口")
@RequestMapping("/htFileInfo")
public class HtFileInfoController extends BaseController {

	public static final String TREE_FIELD_CHILDREN = "children";

	@Autowired
	private HtFileInfoService htFileInfoService;

	@Value("${file.http.delete}")
	private String deleteFileRequestAddress;

	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_file_info列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtFileInfo> page, @RequestBody HtFileInfo htFileInfo) {
		IPage<HtFileInfo> pageList = htFileInfoService.findPage(page, htFileInfo);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_file_info列表",notes="")
	@ApiParam(name = "htFileInfo", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtFileInfo htFileInfo) {
		Wrapper<HtFileInfo> queryWrapper = new QueryWrapper<>(htFileInfo);
		List<HtFileInfo> list = htFileInfoService.list(queryWrapper);
		return Result.ok(list);
	}

	@ApiOperation(value = "获取ht_file_info树",notes="")
	@ApiParam(name = "htFileInfo", value = "实体")
	@RequestMapping(value="/treeData",method = {RequestMethod.GET})
	public Result treeData(HtFileInfo htFileInfo) {
		Wrapper<HtFileInfo> queryWrapper = new QueryWrapper<>(htFileInfo);
		int count = htFileInfoService.count(queryWrapper);
		if(count <= 0){
			HtFileInfo obj = new HtFileInfo();
			obj.setFileName(SystemConstant.ORIGIN_FOLDER_NAME);
			obj.setFileType(SystemConstant.FILE_TYPE_FOLDER);
			htFileInfoService.save(obj);
		}
		List<HtFileInfo> list = htFileInfoService.list(queryWrapper);
		return Result.ok(getTreeData(list));
	}

	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取详情",notes="")
	@RequestMapping(value="/detail",method = {RequestMethod.GET})
	public Result detail(String id) {
		HtFileInfo htFileInfo = htFileInfoService.getById(id);
		return Result.ok(htFileInfo);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htFileInfo", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtFileInfo htFileInfo){
		htFileInfoService.save(htFileInfo);
		return Result.ok();
	}

	/**
	 * 批量保存
	 */
	@ApiOperation(value = "批量保存",notes="")
	@ApiParam(name = "htFileInfo", value = "实体")
	@RequestMapping(value="/batchSave",method = {RequestMethod.POST})
	public Result batchSave(@Valid @RequestBody List<HtFileInfo> htFileInfos){
		htFileInfoService.saveBatch(htFileInfos);
		return Result.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htFileInfo", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtFileInfo htFileInfo){
		htFileInfoService.updateById(htFileInfo);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
        //逻辑删除
		//HtFileInfo htFileInfo = new HtFileInfo();
		//htFileInfo.setMenuId(id);
		//sysMenu.setDelFlag(-1);
		//htFileInfoService.updateById(htFileInfo);
		QueryWrapper<HtFileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
		List<HtFileInfo> list = htFileInfoService.getBaseMapper().selectList(queryWrapper);
		if(list.size() > 0){
			throw new BaseException(messageSource.getMessage("file.error.deleteException", null, LocaleContextHolder.getLocale()));
		}
		HtFileInfo htFileInfo = htFileInfoService.getById(id);
		//物理删除
		htFileInfoService.removeById(id);
		if(SystemConstant.FILE_TYPE_ATTACHMENT.equals(htFileInfo.getFileType())){
			Map<String, String> map = new HashMap<>();
			map.put("address",htFileInfo.getAddress());
			HttpClientUtils.post(deleteFileRequestAddress,map);
		}
		return Result.ok();
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除",notes="")
	@RequestMapping(value="/batchDelete",method = {RequestMethod.POST})
	public Result batchDelete(@RequestBody String[] ids){
		//物理删除
		if(ids==null){
			return Result.error("未选择要删除的数据项！");
		}
		htFileInfoService.removeByIds(Arrays.asList(ids));
		return Result.ok();
	}

	/**
	 * description: 移动文件 <br>
	 * version: 1.0 <br>
	 * date: 2020/5/25 11:43 <br>
	 * author: TYJ <br>
	 */
	@ApiOperation(value = "移动文件",notes="")
	@RequestMapping(value="/moveFile",method = {RequestMethod.POST})
	public Result moveFile(@RequestBody JSONObject jsonParam){
		String target = jsonParam.getString("target");
		String[] ids = jsonParam.getJSONArray("ids").toArray(new String[]{});
		htFileInfoService.moveFile(target, ids);
		return Result.ok();
	}

	/**
	 * description: 将文件list转化为tree结构 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/30 16:42 <br>
	 * author: TYJ <br>
	 */ 
	public List<HtFileInfo> getTreeData(List<HtFileInfo> list) {
		List<HtFileInfo> trees = new ArrayList<>();
		if(list.isEmpty()){
			return trees;
		}
		/**获取一级目录*/
		for(HtFileInfo htFileInfo : list){
			if (StringUtil.isBlank(htFileInfo.getParentId())){
				getChildren(htFileInfo, list);
				trees.add(htFileInfo);
			}
		}
		return trees;
	}

	/**
	 * description: 获取子文件夹 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/30 16:42 <br>
	 * author: TYJ <br>
	 */ 
	public void getChildren(HtFileInfo parent, List<HtFileInfo> list) {
		List<HtFileInfo> children = null;
		for(HtFileInfo htFileInfo : list){
			if(parent.getId().equals(htFileInfo.getParentId())){
				if(parent.getChildren()==null || parent.getChildren().isEmpty()){
					children = new ArrayList<>();
					children.add(htFileInfo);
					parent.setChildren(children);
				}else{
					parent.getChildren().add(htFileInfo);
				}
				getChildren(htFileInfo, list);
			}
		}
	}

}
