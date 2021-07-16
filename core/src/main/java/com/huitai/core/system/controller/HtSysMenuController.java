package com.huitai.core.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.base.BaseException;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysMenu;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysMenuService;
import com.huitai.core.system.service.HtSysUserService;
import com.huitai.core.utils.UserUtil;
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
import java.util.*;


/**
 * description  前端控制器 <br>
 * author XJM <br>
 * date: 2020-04-10 14:35 <br>
 * version: 1.0 <br>
 */



@RestController
@Api(value = "ht_sys_menuCRUD接口")
@RequestMapping("/htSysMenu")
public class HtSysMenuController extends BaseController {

	@Autowired
	private HtSysMenuService htSysMenuService;

	@Autowired
	private HtSysUserService htSysUserService;

	@ApiOperation(value = "获取菜单列表",notes="")
	@ApiParam(name = "htSysMenu", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysMenu htSysMenu) {
		Result result = null;
		QueryWrapper<HtSysMenu> queryWrapper = new QueryWrapper<>();
		if(!StringUtil.isEmpty(htSysMenu.getMenuName()))queryWrapper.like("menu_name", htSysMenu.getMenuName());
		if(!StringUtil.isEmpty(htSysMenu.getMenuType()))queryWrapper.in("menu_type", htSysMenu.getMenuType().split(","));
		List<HtSysMenu> queryList = htSysMenuService.list(queryWrapper);
		if(queryList.isEmpty()){
			result = Result.ok(new ArrayList<>());
			result.put("expandRowKeys", new ArrayList<>());
			return result;
		}else{
			queryWrapper = new QueryWrapper<>();
			queryWrapper.orderByAsc("tree_sort");
			if(!StringUtil.isEmpty(htSysMenu.getMenuType()))queryWrapper.in("menu_type", htSysMenu.getMenuType().split(","));
			List<HtSysMenu> list = htSysMenuService.list(queryWrapper);
			result = Result.ok(htSysMenu.buildByRecursive(list));
			List<String> ids = new ArrayList<>();
			for (HtSysMenu node : queryList) {
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
		HtSysMenu htSysMenu = htSysMenuService.getMenuById(id);
		if(htSysMenu == null){
			throw new BaseException(messageSource.getMessage("system.error.dataNotExist", null, LocaleContextHolder.getLocale()));
		}
		return Result.ok(htSysMenu);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysMenu", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysMenu htSysMenu){
		htSysMenuService.saveOrUpdateHtSysMenu(htSysMenu);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String ids){
		//物理删除
		htSysMenuService.deleteMenu(ids.split(","));
		return Result.ok();
	}

	/**
	 * description: 选择父级菜单树数据 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/17 16:44 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "选择父级菜单树数据",notes="")
	@RequestMapping(value="/pTreeData",method = {RequestMethod.GET})
	public Result pTreeData(String id) {
		List<HtSysMenu> list = htSysMenuService.list();
		HtSysMenu htSysMenu = htSysMenuService.getById(id);
		if(htSysMenu == null){
			htSysMenu= new HtSysMenu();
			List<JSONObject> treeData = htSysMenu.buildByRecursive(list);
			return Result.ok(treeData);
		}

		QueryWrapper<HtSysMenu> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeRight("parent_ids", htSysMenu.getParentIds() + htSysMenu.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
		List<HtSysMenu> childrenList = htSysMenuService.list(queryWrapper);
		String[] excludeIds = null;
		if(!childrenList.isEmpty()){
			excludeIds = new String[childrenList.size() + 1];
			for (int i = 0; i < childrenList.size(); i++) {
				excludeIds[i] = childrenList.get(i).getId();
			}
			excludeIds[childrenList.size()] = htSysMenu.getId();
		}else{
			excludeIds = new String[]{htSysMenu.getId()};
		}

		List<JSONObject> treeData = htSysMenu.buildByRecursive(list, excludeIds);
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
	public Result saveTreeSortData(@RequestBody List<HtSysMenu> htSysMenuList) {
		htSysMenuService.saveTreeSortData(htSysMenuList);
		return Result.ok();
	}

	/**
	 * description: 获取菜单树数据, <br>
	 * version: 1.0 <br>
	 * date: 2020/4/20 14:18 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "菜单树数据，可以根据菜单权重过滤",notes="")
	@RequestMapping(value="/treeData",method = {RequestMethod.POST})
	public Result treeData(@RequestBody HtSysMenu htSysMenu) {
		QueryWrapper<HtSysMenu> queryWrapper = new QueryWrapper<>();
		if(!StringUtil.isEmpty(htSysMenu.getWeight()))queryWrapper.le("weight", htSysMenu.getWeight());
		queryWrapper.orderByAsc("tree_sort");
		List<HtSysMenu> list = htSysMenuService.list(queryWrapper);
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ids.add(list.get(i).getId());
		}
		List<JSONObject> treeData = htSysMenu.buildByRecursive(list);
		List<JSONObject> finalTreeData = new ArrayList<>();
		JSONObject jsonObject = null;
		// 过滤掉父节点不是0的根节点数据
		for (int i = 0; i < treeData.size(); i++) {
			jsonObject = treeData.get(i);
			if(!StringUtil.isEmpty(jsonObject.get("parentId"))
					&& SystemConstant.DEFAULT_PARENTID.equals(jsonObject.get("parentId").toString())){
				finalTreeData.add(jsonObject);
			}
		}
		Result result = Result.ok(finalTreeData);
		// 将所有id返回，用于全选
		result.put("allIds", ids);
		return result;
	}

	/**
	 * description: 获取当前用户拥有的所有菜单和用户信息 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/24 10:45 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "获取当前用户拥有的所有菜单和用户信息",notes="")
	@RequestMapping(value="/getUserMenuTreeData",method = {RequestMethod.GET})
	public Result getUserMenuTreeData(String menuType) {
		Map<String, Object> params = new HashMap<>();
		params.put("menuType", menuType.split(","));
		HtSysMenu htSysMenu = new HtSysMenu();
		HtSysUser htSysUser = UserUtil.getCurUser();
		List<HtSysMenu> list = new ArrayList<>();
		if(UserUtil.isSuperAdmin(htSysUser)){
			params.put("userId", null);
		}else{
			params.put("userId", htSysUser.getId());
		}
		list = htSysMenuService.findUserMenuList(params);
		List<JSONObject> treeData = htSysMenu.buildByRecursive(list);
		htSysUser = htSysUserService.getUserById(htSysUser.getId());
		Result result = Result.ok(treeData);
		result.put("user", htSysUser);

		return result;
	}

}
