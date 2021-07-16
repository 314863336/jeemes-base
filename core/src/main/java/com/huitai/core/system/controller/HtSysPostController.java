package com.huitai.core.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.base.BaseException;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysPost;
import com.huitai.core.system.service.HtSysPostRoleService;
import com.huitai.core.system.service.HtSysPostService;
import com.huitai.core.system.service.HtSysUserPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description 岗位表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-04-16 11:19 <br>
 * version: 1.0 <br>
 */



@RestController
@Api(value = "ht_sys_postCRUD接口")
@RequestMapping("/htSysPost")
public class HtSysPostController extends BaseController {

	@Autowired
	private HtSysPostService htSysPostService;

	@Autowired
	private HtSysUserPostService htSysUserPostService;

	@Autowired
	private HtSysPostRoleService htSysPostRoleService;

	@ApiOperation(value = "获取ht_sys_post列表",notes="")
	@ApiParam(name = "htSysPost", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysPost htSysPost) {
		Result result = null;
		QueryWrapper<HtSysPost> queryWrapper = new QueryWrapper<>();
		if(!StringUtil.isEmpty(htSysPost.getPostName()))queryWrapper.like("post_name", htSysPost.getPostName());
		if(!StringUtil.isEmpty(htSysPost.getPostCode()))queryWrapper.like("post_code", htSysPost.getPostCode());
		if(htSysPost.getPostType() != null)queryWrapper.eq("post_type", htSysPost.getPostType());
		if(!StringUtil.isEmpty(htSysPost.getStatus()))queryWrapper.eq("status", htSysPost.getStatus());
		List<HtSysPost> queryList = htSysPostService.list(queryWrapper);
		if(queryList.isEmpty()){
			result = Result.ok(new ArrayList<>());
			result.put("expandRowKeys", new ArrayList<>());
			return result;
		}else{
			//queryWrapper = new QueryWrapper<>(); //PLF 注释 为解决模糊查询无效
			queryWrapper.orderByAsc("tree_sort");
			List<HtSysPost> list = htSysPostService.list(queryWrapper);
			result = Result.ok(htSysPost.buildByRecursive(list));
			List<String> ids = new ArrayList<>();
			for (HtSysPost node : queryList) {
				String[] parentIds = node.getParentIds().split(SystemConstant.DEFAULT_PARENTIDS_SPLIT);
				ids.addAll(Arrays.asList(parentIds));
			}
			result.put("expandRowKeys", ids);
			return result;
		}
	}

	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取详情",notes="")
	@RequestMapping(value="/detail",method = {RequestMethod.GET})
	public Result detail(String id) {
		HtSysPost htSysPost = htSysPostService.getPostById(id);
		if(htSysPost == null){
			throw new BaseException(messageSource.getMessage("system.error.dataNotExist", null, LocaleContextHolder.getLocale()));
		}
		Result result = Result.ok(htSysPost);
		List<Object> ids = htSysUserPostService.findUsersOfPost(id);
		result.put("userIds", ids);

		List<String> roleIds = htSysPostRoleService.findRolesOfPost(id);
		result.put("roleIds", roleIds);

		return result;
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysPost", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysPost htSysPost){
		htSysPostService.saveOrUpdateHtSysPost(htSysPost);
		Result result = Result.ok(htSysPost);

		List<Object> ids = htSysUserPostService.findUsersOfPost(htSysPost.getId());
		result.put("userIds", ids);

		List<String> roleIds = htSysPostRoleService.findRolesOfPost(htSysPost.getId());
		result.put("roleIds", roleIds);
		return result;
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String ids){
		//物理删除
		htSysPostService.deletePost(ids.split(","));
		return Result.ok();
	}

	/**
	 * description: 选择父级岗位树数据 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/17 16:44 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "选择父级岗位树数据",notes="")
	@RequestMapping(value="/pTreeData",method = {RequestMethod.GET})
	public Result pTreeData(String id) {
		List<HtSysPost> list = htSysPostService.list();
		HtSysPost htSysPost = htSysPostService.getById(id);
		if(htSysPost == null){
			htSysPost= new HtSysPost();
			List<JSONObject> treeData = htSysPost.buildByRecursive(list);
			return Result.ok(treeData);
		}

		QueryWrapper<HtSysPost> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeRight("parent_ids", htSysPost.getParentIds() + htSysPost.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
		List<HtSysPost> childrenList = htSysPostService.list(queryWrapper);
		String[] excludeIds = null;
		if(!childrenList.isEmpty()){
			excludeIds = new String[childrenList.size() + 1];
			for (int i = 0; i < childrenList.size(); i++) {
				excludeIds[i] = childrenList.get(i).getId();
			}
			excludeIds[childrenList.size()] = htSysPost.getId();
		}else{
			excludeIds = new String[]{htSysPost.getId()};
		}



		List<JSONObject> treeData = htSysPost.buildByRecursive(list, excludeIds);
		return Result.ok(treeData);
	}

	/**
	 * description: 保存列表排序 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/17 16:44 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "保存列表排序",notes="")
	@RequestMapping(value="/saveTreeSortData",method = {RequestMethod.POST})
	public Result saveTreeSortData(@RequestBody List<HtSysPost> htSysMenuList) {
		htSysPostService.saveTreeSortData(htSysMenuList);
		return Result.ok();
	}

	/**
	 * description: 岗位树数据 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/20 16:37 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "岗位树数据，可以根据岗位类型过滤",notes="")
	@RequestMapping(value="/treeData",method = {RequestMethod.POST})
	public Result treeData(@RequestBody HtSysPost htSysPost) {
		QueryWrapper<HtSysPost> queryWrapper = new QueryWrapper<>();
		if(!StringUtil.isEmpty(htSysPost.getPostType()))queryWrapper.le("post_type", htSysPost.getPostType());
		List<HtSysPost> list = htSysPostService.list(queryWrapper);
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ids.add(list.get(i).getId());
		}
		List<JSONObject> treeData = htSysPost.buildByRecursive(list);

		Result result = Result.ok(treeData);
		// 将所有id返回，用于全选和反选
		result.put("allIds", ids);
		return result;
	}

	/**
	 * description: 保存分配用户 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/22 15:08 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "保存分配用户",notes="")
	@RequestMapping(value="/saveUserPost",method = {RequestMethod.POST})
	public Result saveUserPost(@RequestBody List<String> ids, String id){
		htSysUserPostService.saveUserPost(ids, id);
		return Result.ok();
	}

	/**
	 * description: 删除分配用户 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/22 16:34 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "删除分配用户",notes="")
	@RequestMapping(value="/deleteUserPost",method = {RequestMethod.GET})
	public Result deleteUserPost(String userId, String id){
		htSysUserPostService.deleteUserPost(userId, id);
		Result result = Result.ok();
		List<Object> ids = htSysUserPostService.findUsersOfPost(id);
		result.put("userIds", ids);
		return result;
	}

	/**
	 * description: 保存分配用户 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/22 15:08 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "保存分配用户",notes="")
	@RequestMapping(value="/savePostRole",method = {RequestMethod.POST})
	public Result savePostRole(@RequestBody List<String> ids, String id){
		htSysPostRoleService.savePostRole(ids, id);
		return Result.ok();
	}

	/**
	 * description: 删除分配角色 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/22 16:34 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "删除分配角色",notes="")
	@RequestMapping(value="/deletePostRole",method = {RequestMethod.GET})
	public Result deletePostRole(String roleId, String id){
		htSysPostRoleService.deletePostRole(roleId, id);
		Result result = Result.ok();
		List<String> roleIds = htSysPostRoleService.findRolesOfPost(id);
		result.put("roleIds", roleIds);
		return result;
	}


}
