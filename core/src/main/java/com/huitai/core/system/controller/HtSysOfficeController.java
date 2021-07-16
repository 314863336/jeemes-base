package com.huitai.core.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.model.Result;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.base.BaseException;
import com.huitai.core.exception.SystemException;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysDictType;
import com.huitai.core.system.entity.HtSysOffice;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysOfficeService;
import com.huitai.core.system.service.HtSysUserService;
import com.huitai.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * description  前端控制器 <br>
 * author TYJ <br>
 * date: 2020-04-15 09:04 <br>
 * version: 1.0 <br>
 */


 
@RestController
@Api(value = "ht_sys_officeCRUD接口")
@RequestMapping("/htSysOffice")
public class HtSysOfficeController extends BaseController {

	@Autowired
	private HtSysOfficeService htSysOfficeService;

	@Autowired
	private HtSysUserService htSysUserService;
	
	/**
	 * 分页
	 */
	@ApiOperation(value = "分页获取ht_sys_office列表",notes="")
	@ApiParam(name = "page", value = "分页参数", required = true)
	@RequestMapping(value="/page",method = {RequestMethod.POST})
	public Result page(Page<HtSysOffice> page) {
		HtSysOffice htSysOffice = new HtSysOffice();
		Wrapper<HtSysOffice> queryWrapper = new QueryWrapper<HtSysOffice>(htSysOffice);
		IPage<HtSysOffice> pageList = htSysOfficeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * description: 获取当前用户下的机构 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/29 10:57 <br>
	 * author: XJM <br>
	 */
	@ApiOperation(value = "获取ht_sys_office列表",notes="")
	@ApiParam(name = "htSysOffice", value = "实体")
	@RequestMapping(value="/list",method = {RequestMethod.POST})
	public Result list(@RequestBody HtSysOffice htSysOffice) {
		Result result = null;
		HtSysUser htSysUser = UserUtil.getCurUser();
		if(htSysUser.getCompany() != null){
			if(htSysOffice.getParams() == null)htSysOffice.setParams(new HashMap<>());
			htSysOffice.getParams().put("parentIds", htSysUser.getCompany().getParentIds() + htSysUser.getCompany().getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
			htSysOffice.getParams().put("companyId", htSysUser.getCompany().getId());
		}
		List<HtSysOffice> queryList = htSysOfficeService.findOfficeList(htSysOffice);
		if(queryList.isEmpty()){
			result = Result.ok(new ArrayList<>());
			result.put("expandRowKeys", new ArrayList<>());
			return result;
		}else{
//			htSysOffice.setOfficeCode(null);
//			htSysOffice.setOfficeName(null);
//			List<HtSysOffice> list = htSysOfficeService.findOfficeList(htSysOffice);
			result = Result.ok(htSysOffice.buildByRecursive(queryList));
			List<String> ids = new ArrayList<>();
			for (HtSysOffice node : queryList) {
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
		HtSysOffice htSysOffice = htSysOfficeService.detail(id);

		return Result.ok(htSysOffice);
	}
	
	/**
	 * 保存或修改
	 */
	@ApiOperation(value = "保存或修改",notes="")
	@ApiParam(name = "htSysOffice", value = "实体")
	@RequestMapping(value="/saveOrUpdateTree",method = {RequestMethod.POST})
	public Result saveOrUpdateTree(@Valid @RequestBody HtSysOffice htSysOffice) {
		if (HtSysOfficeService.OFFICE_TYPE.equals(htSysOffice.getOfficeType())) {
			HtSysOffice pHtSysOffice = htSysOfficeService.getById(htSysOffice.getParentId());
			if (pHtSysOffice != null) {
				if (HtSysOfficeService.OFFICE_TYPE.equals(pHtSysOffice.getOfficeType()) && !pHtSysOffice.getCompanyId().equals(htSysOffice.getCompanyId())) {
					throw new BaseException(messageSource.getMessage("system.error.addOfficeException", null, LocaleContextHolder.getLocale()));
				}
			}
		}
		HtSysOffice old = htSysOfficeService.getById(htSysOffice.getId());

		QueryWrapper<HtSysOffice> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("office_code", htSysOffice.getOfficeCode());
		queryWrapper.ne("id", htSysOffice.getId());
		HtSysOffice get = htSysOfficeService.getOne(queryWrapper);
		if (get != null) {
			throw new SystemException(messageSource.getMessage("system.error.office.codeExist", null, LocaleContextHolder.getLocale()));
		} else {
			htSysOfficeService.saveOrUpdateForTreeFields(htSysOffice);
			return Result.ok();
		}
	}
	/**
	 * 保存
	 */
	@ApiOperation(value = "保存",notes="")
	@ApiParam(name = "htSysOffice", value = "实体")
	@RequestMapping(value="/save",method = {RequestMethod.POST})
	public Result save(@Valid @RequestBody HtSysOffice htSysOffice){
		htSysOfficeService.save(htSysOffice);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改",notes="")
	@ApiParam(name = "htSysOffice", value = "实体")
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	public Result update(@Valid @RequestBody HtSysOffice htSysOffice){
		htSysOfficeService.updateById(htSysOffice);
		return Result.ok();
	}
	
	/**
	 * 删除树数据
	 */
	@ApiOperation(value = "删除树",notes="")
	@RequestMapping(value="/deleteTree",method = {RequestMethod.GET})
	public Result deleteTree(String id){
		HtSysOffice htSysOffice = htSysOfficeService.getById(id);
		if(htSysOffice != null){
			// 如果不是最末级，则不可删除
			if(SystemConstant.NO.equals(htSysOffice.getTreeLeaf())){
				throw new BaseException(messageSource.getMessage("system.error.existsChildsOffice", null, LocaleContextHolder.getLocale()));
			}
			QueryWrapper<HtSysUser> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("company_id", htSysOffice.getId()).or()
					.eq("dept_id", htSysOffice.getId());
			int count = htSysUserService.count(queryWrapper);
			if(count > 0){
				throw new BaseException(messageSource.getMessage("system.error.existsAssociatedUser", null, LocaleContextHolder.getLocale()));
			}
			htSysOfficeService.deleteForTreeFields(id, true);
		}

		return Result.ok();
	}
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除",notes="")
	@RequestMapping(value="/delete",method = {RequestMethod.GET})
	public Result delete(String id){
        //逻辑删除
		//HtSysOffice htSysOffice = new HtSysOffice();
		//htSysOffice.setMenuId(id);
		//sysMenu.setDelFlag(-1);
		//htSysOfficeService.updateById(htSysOffice);
		//物理删除
		htSysOfficeService.removeById(id);
		return Result.ok();
	}

	@ApiOperation(value = "懒加载获取机构树",notes="")
	@ApiParam(name = "htSysOffice", value = "实体")
	@RequestMapping(value="/treeDataLazy",method = {RequestMethod.POST})
	public Result treeDataLazy(@RequestBody HtSysOffice htSysOffice) {
		try {
			if(StringUtil.isEmpty(htSysOffice.getOfficeName())
					&& StringUtil.isEmpty(htSysOffice.getOfficeCode())
					&& StringUtil.isEmpty(htSysOffice.getParentId())){
				htSysOffice.setParentId(SystemConstant.DEFAULT_PARENTID);
			}
            List<HtSysOffice> list = htSysOfficeService.findOfficeList(htSysOffice);
			Result result = Result.ok(htSysOffice.buildByRecursiveLazy(list));
			List<String> ids = new ArrayList<>();
			for (HtSysOffice node : list) {
				String[] parentIds = node.getParentIds().split(SystemConstant.DEFAULT_PARENTIDS_SPLIT);
				ids.addAll(Arrays.asList(parentIds));
			}
			result.put("expandRowKeys", ids);

			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return Result.error("系统出错，请联系管理员！");
		}
	}

	/*
	 * description: 选择上级机构树（排除当前节点和子节点） filterbyCurrentUser 是否根据当前用胡过滤机构 <br>
	 * version: 1.0 <br>
	 * date: 2020/4/15 9:08 <br>
	 * author: TYJ <br>
	 */
	@ApiOperation(value = "获取树结构列表",notes="")
	@RequestMapping(value="/treeData",method = {RequestMethod.GET})
	public Result treeData(String id, String officeType, @RequestParam(required = false) boolean filterbyCurrentUser) {
        QueryWrapper<HtSysOffice> queryWrapper = new QueryWrapper<>();
        if(!StringUtil.isEmpty(officeType)){
            queryWrapper.eq("office_type", officeType);
        }
        // 如果根据当前用户过滤组织机构，则获取当前用户所属公司和子公司，以及下面的部门
        if(filterbyCurrentUser){
			HtSysUser htSysUser = UserUtil.getCurUser();
			if(htSysUser.getCompany() != null){
				queryWrapper.likeRight("parent_ids", htSysUser.getCompany().getParentIds() + htSysUser.getCompany().getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT).or()
						.eq("id", htSysUser.getCompany().getId());
			}

		}

        List<HtSysOffice> list = htSysOfficeService.list(queryWrapper);
		HtSysOffice htSysOffice = null;
        if(StringUtil.isNotBlank(id)){
			htSysOffice = htSysOfficeService.getById(id);
		}
        if(htSysOffice == null){
            htSysOffice= new HtSysOffice();
            List<JSONObject> treeData = htSysOffice.buildByRecursive(list);
            return Result.ok(treeData);
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("parent_ids", htSysOffice.getParentIds() + htSysOffice.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
        List<HtSysOffice> childrenList = htSysOfficeService.list(queryWrapper);
        String[] excludeIds = null;
        if(!childrenList.isEmpty()){
            excludeIds = new String[childrenList.size() + 1];
            for (int i = 0; i < childrenList.size(); i++) {
                excludeIds[i] = childrenList.get(i).getId();
            }
            excludeIds[childrenList.size()] = htSysOffice.getId();
        }else{
            excludeIds = new String[]{htSysOffice.getId()};
        }

        List<JSONObject> treeData = htSysOffice.buildByRecursive(list, excludeIds);
        return Result.ok(treeData);
	}

}
