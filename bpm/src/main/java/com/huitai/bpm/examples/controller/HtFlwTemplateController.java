package com.huitai.bpm.examples.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.bpm.examples.entity.HtFlwTemplate;
import com.huitai.bpm.examples.service.HtFlwTemplateService;
import com.huitai.common.model.Result;
import com.huitai.core.base.BaseController;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * description 流程使用样板 前端控制器 <br>
 * author PLF <br>
 * date: 2020-12-09 15:48 <br>
 * version: 1.0 <br>
 */

@RestController
@Api(value = "ht_flw_templateCRUD接口")
@RequestMapping("/htFlwTemplate")
public class HtFlwTemplateController extends BaseController {

    @Autowired
    private HtFlwTemplateService htFlwTemplateService;

    @PostMapping("start")
    public Result start(HtFlwTemplate htFlwTemplate, @RequestBody Map<String, Object> params) {
        return Result.ok(htFlwTemplateService.startProcess(htFlwTemplate, params));
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页获取ht_flw_template列表", notes = "")
    @ApiParam(name = "page", value = "分页参数", required = true)
    @RequestMapping(value = "/page", method = {RequestMethod.POST})
    public Result page(Page<HtFlwTemplate> page, @RequestBody HtFlwTemplate htFlwTemplate) {
        QueryWrapper<HtFlwTemplate> queryWrapper = new QueryWrapper<>(htFlwTemplate);
        HtSysUser htSysUser = UserUtil.getCurUser();
        if (htSysUser != null) {
            //数据只对流程参与过的、当前待办的、以及数据创建人可见 处理
            queryWrapper.like("assignees", "," + htSysUser.getLoginCode() + ",").or().like("create_by", htSysUser.getLoginCode());
        }
        IPage<HtFlwTemplate> pageList = htFlwTemplateService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @ApiOperation(value = "获取ht_flw_template列表", notes = "")
    @ApiParam(name = "htFlwTemplate", value = "实体")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Result list(@RequestBody HtFlwTemplate htFlwTemplate) {
        Wrapper<HtFlwTemplate> queryWrapper = new QueryWrapper<>(htFlwTemplate);
        List<HtFlwTemplate> list = htFlwTemplateService.list(queryWrapper);
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
        HtFlwTemplate htFlwTemplate = htFlwTemplateService.getById(id);
        return Result.ok(htFlwTemplate);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "")
    @ApiParam(name = "htFlwTemplate", value = "实体")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Result save(@Valid @RequestBody HtFlwTemplate htFlwTemplate) {
        htFlwTemplateService.save(htFlwTemplate);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "")
    @ApiParam(name = "htFlwTemplate", value = "实体")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public Result update(@Valid @RequestBody HtFlwTemplate htFlwTemplate) {
        htFlwTemplateService.updateById(htFlwTemplate);
        return Result.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public Result delete(String id) {
        //物理删除
        htFlwTemplateService.removeById(id);
        return Result.ok();
    }

}
