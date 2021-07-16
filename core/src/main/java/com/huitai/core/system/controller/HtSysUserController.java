package com.huitai.core.system.controller;

import com.alibaba.excel.EasyExcel;
import com.huitai.common.model.Result;
import com.huitai.common.utils.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseController;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysOffice;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.listener.HtSysUserListener;
import com.huitai.core.system.service.HtSysConfigService;
import com.huitai.core.system.service.HtSysOfficeService;
import com.huitai.core.system.service.HtSysUserPostService;
import com.huitai.core.system.service.HtSysUserService;
import com.huitai.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;


/**
 * description  前端控制器 <br>
 * author XJM <br>
 * date: 2020-04-08 10:26 <br>
 * version: 1.0 <br>
 */
@RestController
@Api(value = "ht_sys_userCRUD接口")
@RequestMapping("/htSysUser")
public class HtSysUserController extends BaseController {

    @Autowired
    private HtSysOfficeService htSysOfficeService;

    @Autowired
    private HtSysUserService htSysUserService;

    @Autowired
    private HtSysUserPostService htSysUserPostService;

    @Autowired
    private HtSysConfigService htSysConfigService;

    @RequestMapping(value = "/pageByPosts", method = {RequestMethod.POST})
    public Result pageByPosts(@RequestBody Page<HtSysUser> page, String posts, HtSysUser htSysUser) {
        return Result.ok(htSysUserService.pageByPosts(page, posts, htSysUser));
    }

    /**
     * 分页
     */
    @ApiOperation(value = "获取ht_sys_user列表", notes = "")
    @ApiParam(name = "page", value = "分页参数", required = true)
    @RequestMapping(value = "/page", method = {RequestMethod.POST})
    public Result page(@RequestBody Page<HtSysUser> page) {
        // 如果存在filterbyCurrentUser参数为true, 只能看到当前公司以及子公司下的用户
        if (page.getExtraParams() != null && page.getExtraParams().get("filterbyCurrentUser") != null) {
            boolean filterbyCurrentUser = Boolean.getBoolean(page.getExtraParams().get("filterbyCurrentUser").toString());
            if (filterbyCurrentUser) {
                HtSysUser htSysUser = UserUtil.getCurUser();
                if (htSysUser.getCompany() != null) {
                    if (page.getExtraParams() == null) {
                        page.setExtraParams(new HashMap<>());
                    }
                    page.getExtraParams().put("parentIds", htSysUser.getCompany().getParentIds() + htSysUser.getCompany().getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
                    page.getExtraParams().put("companyId", htSysUser.getCompany().getId());
                }
            }
        }

        // 如果参数中存在公司或部门id,则查询该机构以及子机构下的人员
        HtSysUser params = page.getParams();
        if (params != null) {
            if (StringUtil.isNotBlank(params.getCompanyId())) {
                HtSysOffice htSysOffice = htSysOfficeService.getById(params.getCompanyId());
                if (page.getExtraParams() == null) {
                    page.setExtraParams(new HashMap<>());
                }
                page.getExtraParams().put("parentIds", htSysOffice.getParentIds() + htSysOffice.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
                page.getExtraParams().put("companyId", htSysOffice.getId());
            } else if (StringUtil.isNotBlank(params.getDeptId())) {
                HtSysOffice htSysOffice = htSysOfficeService.getById(params.getDeptId());
                if (page.getExtraParams() == null) {
                    page.setExtraParams(new HashMap<>());
                }
                page.getExtraParams().put("parentIds", htSysOffice.getParentIds() + htSysOffice.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
                page.getExtraParams().put("deptId", htSysOffice.getId());
            }
        }
        Page<HtSysUser> pageList = htSysUserService.findHtSysUserList(page);
        Result result = Result.ok(pageList);
        return result;
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
        HtSysUser htSysUser = htSysUserService.getUserById(id);

        List<Object> postIds = htSysUserPostService.findPostsOfUser(htSysUser.getId());
        Result result = Result.ok(htSysUser);
        result.put("postIds", postIds);
        return result;
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Result save(@ApiParam(name = "htSysUser", value = "实体") @Valid @RequestBody HtSysUser htSysUser) {
        htSysUserService.saveOrUpdateUser(htSysUser);
        List<Object> postIds = htSysUserPostService.findPostsOfUser(htSysUser.getId());
        Result result = Result.ok(htSysUser);
        result.put("postIds", postIds);
        return result;
    }

    /**
     * 上传头像
     */
    @ApiOperation(value = "获取头像", notes = "")
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public Result upload(MultipartFile file) {
        if (file == null || StringUtil.isEmpty(file.getName())) {

        }
        File filePath = new File(HtSysUserController.class.getResource("/").getPath() + File.separator + "static" + File.separator + "images");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = s + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            file.transferTo(Paths.get(filePath + File.separator + fileName));
            return Result.ok(File.separator + "images" + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public Result delete(String ids) {
        //物理删除
        htSysUserService.deleteUser(ids.split(","));
        return Result.ok();
    }

    /**
     * description: 批量删除 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 10:44 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @RequestMapping(value = "/deleteBatch", method = {RequestMethod.GET})
    public Result deleteBatch(@ApiParam(name = "ids[]", value = "id数组") @RequestParam(value = "ids[]") String[] ids) {
        //物理删除
        htSysUserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * description: 启用 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:29 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "启用", notes = "启用")
    @RequestMapping(value = "/enable", method = {RequestMethod.GET})
    public Result enable(@ApiParam(name = "id", value = "id") String id) {
        // 启用
        htSysUserService.updateStatus(id, SystemConstant.ENABLE);
        return Result.ok();
    }

    /**
     * description: 停用 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:29 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "停用", notes = "停用")
    @RequestMapping(value = "/disable", method = {RequestMethod.GET})
    public Result disable(@ApiParam(name = "id", value = "id") String id) {
        // 停用
        htSysUserService.updateStatus(id, SystemConstant.DISABLE);
        return Result.ok();
    }

    /**
     * description: 修改密码 encodePassword为前端md5加密过的密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 13:54 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @RequestMapping(value = "/updatePassword", method = {RequestMethod.GET})
    public Result updatePassword(@ApiParam(name = "id", value = "id") String id, @ApiParam(name = "encodePassword", value = "md5加密过的密码") String encodePassword) {
        // 修改密码
        htSysUserService.updatePassword(id, encodePassword);
        return Result.ok();
    }

    /**
     * description: 重置密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 13:54 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @RequestMapping(value = "/resetPassword", method = {RequestMethod.GET})
    public Result resetPassword(@ApiParam(name = "id", value = "id") String id) {
        // 重置密码
        htSysUserService.resetPassword(id);
        return Result.ok();
    }

    /**
     * description: 保存分配岗位, ids：岗位ids， id：role_id <br>
     * version: 1.0 <br>
     * date: 2020/4/20 16:07 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "保存分配岗位", notes = "")
    @RequestMapping(value = "/assignPost", method = {RequestMethod.GET})
    public Result assignPost(String ids, String id) {
        htSysUserPostService.assignPost(ids.split(","), id);
        return Result.ok();
    }

    /**
     * description: 导出用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 14:53 <br>
     * author: TYJ <br>
     */
    @ApiOperation(value = "导出用户", notes = "")
    @RequestMapping(value = "/download", method = {RequestMethod.GET})
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户", "UTF-8");
        // 根据用户传入字段 假设我们要忽略 companyId
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("companyId");
        excludeColumnFiledNames.add("deptId");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> map = new HashMap<>();
        for (String key : params.keySet()) {
            map.put(key, params.get(key)[0]);
        }
        List<HtSysUser> list = htSysUserService.listExcel(map);
        EasyExcel.write(response.getOutputStream(), HtSysUser.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("用户").doWrite(list);
    }

    /**
     * description: 导出用户模板 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 14:53 <br>
     * author: TYJ <br>
     */
    @ApiOperation(value = "导出用户模板", notes = "")
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET})
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户模板", "UTF-8");
        // 根据用户传入字段 假设我们要忽略 companyId
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("companyId");
        excludeColumnFiledNames.add("deptId");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), HtSysUser.class)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet("用户").doWrite(new ArrayList<HtSysUser>());
    }

    /**
     * description: 导入用户 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 15:01 <br>
     * author: TYJ <br>
     */
    @ApiOperation(value = "导入用户", notes = "")
    @RequestMapping(value = "/importData", method = {RequestMethod.POST})
    public Result importData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), HtSysUser.class,
                new HtSysUserListener(htSysUserService, htSysConfigService, htSysOfficeService))
                .sheet().doRead();
        return Result.ok("导入成功");
    }
}
