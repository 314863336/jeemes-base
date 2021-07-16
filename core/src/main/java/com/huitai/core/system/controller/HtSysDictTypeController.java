package com.huitai.core.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.core.base.BaseController;
import com.huitai.core.exception.SystemException;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysDictType;
import com.huitai.core.system.service.HtSysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * description 数据字典表 前端控制器 <br>
 * author TYJ <br>
 * date: 2020-04-20 15:02 <br>
 * version: 1.0 <br>
 */


@RestController
@Api(value = "ht_sys_dict_typeCRUD接口")
@RequestMapping("/htSysDictType")
public class HtSysDictTypeController extends BaseController {

    @Autowired
    private HtSysDictTypeService htSysDictTypeService;

    /**
     * 分页
     */
    @ApiOperation(value = "分页获取ht_sys_dict_type列表", notes = "")
    @ApiParam(name = "page", value = "分页参数", required = true)
    @RequestMapping(value = "/page", method = {RequestMethod.POST})
    public Result page(@RequestBody Page<HtSysDictType> page) {
        HtSysDictType htSysDictType = page.getParams();
        QueryWrapper<HtSysDictType> queryWrapper = new QueryWrapper<>();
        if (htSysDictType != null) {
            if (StringUtils.isNotBlank(htSysDictType.getDictName())) {
                queryWrapper.like("dict_name", htSysDictType.getDictName());
            }
            if (StringUtils.isNotBlank(htSysDictType.getDictCode())) {
                queryWrapper.like("dict_code", htSysDictType.getDictCode());
            }
            if (StringUtils.isNotBlank(htSysDictType.getStatus())) {
                queryWrapper.eq("status", htSysDictType.getStatus());
            }
        }
        Page<HtSysDictType> pageList = htSysDictTypeService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取ht_sys_dict_type列表", notes = "")
    @ApiParam(name = "htSysDictType", value = "实体")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Result list(@RequestBody HtSysDictType htSysDictType) {
        Wrapper<HtSysDictType> queryWrapper = new QueryWrapper<>(htSysDictType);
        List<HtSysDictType> list = htSysDictTypeService.list(queryWrapper);
        return Result.ok(list);
    }

    /**
     * 根据id获取详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详情", notes = "")
    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    public Result detail(String id) {
        HtSysDictType htSysDictType = htSysDictTypeService.getById(id);
        return Result.ok(htSysDictType);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "")
    @ApiParam(name = "htSysDictType", value = "实体")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Result save(@RequestBody HtSysDictType htSysDictType) {
        QueryWrapper<HtSysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", htSysDictType.getDictCode());
        HtSysDictType get = htSysDictTypeService.getOne(queryWrapper);
        if (get != null) {
            throw new SystemException(messageSource.getMessage("system.error.dict.dataExist", null, LocaleContextHolder.getLocale()));
        } else {
            htSysDictTypeService.save(htSysDictType);
        }
        return Result.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "")
    @ApiParam(name = "htSysDictType", value = "实体")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public Result update(@RequestBody HtSysDictType htSysDictType) {
        htSysDictTypeService.update(htSysDictType);
        return Result.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public Result delete(String id) {
        //逻辑删除
        //HtSysDictType htSysDictType = new HtSysDictType();
        //htSysDictType.setMenuId(id);
        //sysMenu.setDelFlag(-1);
        //htSysDictTypeService.updateById(htSysDictType);
        //物理删除
        htSysDictTypeService.delete(id);
        return Result.ok();
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除", notes = "")
    @RequestMapping(value = "/batchDeleteType", method = {RequestMethod.POST})
    public Result batchDeleteType(@RequestBody String[] ids) {
        if(ids==null){
            return Result.error("未选择要删除的数据项！");
        }
        //物理删除
        htSysDictTypeService.removeByIds(Arrays.asList(ids));
        return Result.ok("删除成功！");
    }

    /**
     * 启用
     */
    @ApiOperation(value = "启用", notes = "")
    @RequestMapping(value = "/enable", method = {RequestMethod.GET})
    public Result enable(String id) {
        UpdateWrapper<HtSysDictType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", SystemConstant.ENABLE);
        updateWrapper.eq("id", id);
        htSysDictTypeService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用", notes = "")
    @RequestMapping(value = "/disable", method = {RequestMethod.GET})
    public Result disable(String id) {
        UpdateWrapper<HtSysDictType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", SystemConstant.DISABLE);
        updateWrapper.eq("id", id);
        htSysDictTypeService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 刷新字典缓存
     */
    @ApiOperation(value = "刷新字典缓存", notes = "")
    @RequestMapping(value = "/refreshDictCache", method = {RequestMethod.GET})
    public Result refreshDictCache(){
        htSysDictTypeService.refreshDictCache();
        return Result.ok("操作成功！");
    }

}
