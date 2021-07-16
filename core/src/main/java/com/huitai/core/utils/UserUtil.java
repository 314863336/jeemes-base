package com.huitai.core.utils;

import com.huitai.core.security.utils.CustomUser;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.system.service.HtSysUserService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * description: 获取当前用户相关信息 <br>
 * date: 2020/4/24 9:29 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class UserUtil {
    /**
     * description: 获取当前用户信息, 用户信息中包括公司和部门信息，超级管理员公司和部门信息为空 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:32 <br>
     * author: XJM <br>
     */
    public static HtSysUser getCurUser(){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            return null;
        }
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  String){
            return null;
        }
        CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUser.getToken().getHtSysUser();
    }

    /**
     * description: 获取当前用户的公司数据权限信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:32 <br>
     * author: XJM <br>
     */
    public static Set<String> getCurUserCompanyDataScopeSet(){
        CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customUser.getToken().getCompanyDataScope();
    }

    /**
     * description: 获取当前用户的部门权限信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 9:32 <br>
     * author: XJM <br>
     */
    public static Set<String> getCurUserDeptDataScopeSet(){
        CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customUser.getToken().getDeptDataScope();
    }

    /**
     * description: 判断是否超级管理员 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 16:05 <br>
     * author: XJM <br>
     */
    public static boolean isSuperAdmin(HtSysUser htSysUser){
        if(HtSysUserService.USER_TYPE_3 == htSysUser.getUserType()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * description: 判断是否系统管理员 <br>
     * version: 1.0 <br>
     * date: 2020/4/24 16:05 <br>
     * author: XJM <br>
     */
    public static boolean isAdmin(HtSysUser htSysUser){
        if(HtSysUserService.USER_TYPE_2 == htSysUser.getUserType()){
            return true;
        }else{
            return false;
        }
    }


}
