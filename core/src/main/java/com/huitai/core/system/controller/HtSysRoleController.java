package com.huitai.core.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.system.entity.HtSysDataScope;
import com.huitai.core.system.entity.HtSysRole;
import com.huitai.core.system.service.HtSysDataScopeService;
import com.huitai.core.system.service.HtSysPostRoleService;
import com.huitai.core.system.service.HtSysRoleMenuService;
import com.huitai.core.system.service.HtSysRoleService;
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
 * description 角色表 前端控制器 <br>
 * author XJM <br>
 * date: 2020-04-16 13:44 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_sys_roleCRUD接口")
@RequestMapping("/htSysRole")
public class HtSysRoleController extends BaseController {

	@Autowired
	private HtSysRoleService htSysRoleService;

	@Autowired
	private HtSysRoleMenuService htSysRoleMenuService;

	@Autowired
	private HtSysPostRoleService htSysPostRoleService;

	@Autowired
	private HtSysDataScopeService htSysDataScopeService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_role列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(@RequestBody Page<HtSysRole> page) {
		Page<HtSysRole> pageList = htSysRoleService.findHtSysRoleList(page);
		return Result.ok(pageList);
	}

	@ApiOperation(value = "获取ht_sys_role列表",notes="")
	@ApiParam(name = "htSysRole", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysRole htSysRole) {
		Wrapper<HtSysRole> queryWrapper = new QueryWrapper<>(htSysRole);
		List<HtSysRole> list = htSysRoleService.list(queryWrapper);
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
		HtSysRole htSysRole = htSysRoleService.getById(id);
		Result result = Result.ok(htSysRole);
		result.put("menuIds", htSysRoleMenuService.findMenusOfRole(id));
		result.put("postIds", htSysPostRoleService.findPostsOfRole(id));
		if(HtSysRoleService.IS_CTRL_CUSTOME.equals(htSysRole.getIsCtrl())){
			result.put("dataScopeIds", htSysDataScopeService.findDataScopesOfRole(htSysRole.getId()));
		}else{
			result.put("dataScopeIds", new ArrayList<>());
		}

		return result;
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysRole", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysRole htSysRole){
		htSysRoleService.saveOrUpdateHtSysRole(htSysRole);

		htSysRole = htSysRoleService.getById(htSysRole.getId());
		Result result = Result.ok(htSysRole);
		result.put("menuIds", htSysRoleMenuService.findMenusOfRole(htSysRole.getId()));
		result.put("postIds", htSysPostRoleService.findPostsOfRole(htSysRole.getId()));
		if(HtSysRoleService.IS_CTRL_CUSTOME.equals(htSysRole.getIsCtrl())){
			result.put("dataScopeIds", htSysDataScopeService.findDataScopesOfRole(htSysRole.getId()));
		}else{
			result.put("dataScopeIds", new ArrayList<>());
		}

		return result;
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String ids){
		//物理删除
		htSysRoleService.deleteRole(ids.split(","));
		return Result.ok();
	}

	/**
	 * description: 保存分配菜单, ids：菜单ids， id：role_id <br>
	 * version: 1.0 <br>
	 * date: 2020/4/20 16:07 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "保存分配菜单",notes="")
	@RequestMapping(value="/assignMenu",method = {RequestMethod.GET})
	public Result assignMenu(String ids, String id){
		htSysRoleMenuService.assignMenu(ids.split(","), id);
		return Result.ok();
	}

	/**
	 * description: 保存分配岗位, ids：岗位ids， id：role_id <br>
	 * version: 1.0 <br>
	 * date: 2020/4/20 16:07 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "保存分配岗位",notes="")
	@RequestMapping(value="/assignPost",method = {RequestMethod.GET})
	public Result assignPost(String ids, String id){
		htSysPostRoleService.assignPost(ids.split(","), id);
		return Result.ok();
	}

	@ApiOperation(value = "保存分配数据权限",notes="")
	@RequestMapping(value="/assignDataScope",method = {RequestMethod.POST})
	public Result assignDataScope(@RequestBody(required = false) List<HtSysDataScope> htSysDataScope, String isCtrl, String roleId){
		htSysRoleService.assignDataScope(htSysDataScope, roleId, isCtrl);
		return Result.ok();
	}


}
