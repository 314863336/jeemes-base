package com.huitai.core.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.security.exception.CustomUsernameNotFoundException;
import com.huitai.core.security.utils.CustomPasswordEncoder;
import com.huitai.core.security.utils.CustomUser;
import com.huitai.core.security.utils.Token;
import com.huitai.core.system.entity.*;
import com.huitai.core.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.huitai.core.security.consts.SecurityConst.USER_DISABLE_ERROR_CODE;
import static com.huitai.core.security.consts.SecurityConst.USER_NAME_NOT_FOUND_ERROR_CODE;

/**
 * description: UserDetailsService <br>
 * date: 2020/4/23 13:50 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private HtSysUserService htSysUserService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private HtSysMenuService htSysMenuService;

    @Autowired
    private HtSysRoleService htSysRoleService;

    @Autowired
    private HtSysOfficeService htSysOfficeService;

    @Autowired
    private HtSysDataScopeService htSysDataScopeService;

    @Autowired
    protected MessageSource messageSource; //国际化操作

    /**
     * description: 创建存储token的值 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:49 <br>
     * author: XJM <br>
     */
    public Token createToken(UserDetails user) {
        CustomUser customUser = (CustomUser)user;
        Token token = customUser.getToken();
        HtSysUser htSysUser = token.getHtSysUser();
        if(StringUtil.isNotBlank(htSysUser.getDeptId())){
            htSysUser.setDept(htSysOfficeService.getById(htSysUser.getDeptId()));
        }
        if(StringUtil.isNotBlank(htSysUser.getCompanyId())){
            htSysUser.setCompany(htSysOfficeService.getById(htSysUser.getCompanyId()));
        }

        Set<String> companyDataScope = new HashSet<>();
        Set<String> deptDataScope = new HashSet<>();

        getUserDataScope(companyDataScope, deptDataScope, htSysUser);


        token.setCompanyDataScope(companyDataScope);
        token.setDeptDataScope(deptDataScope);
        return token;
    }

    /**
     * description: 获取用户的公司和部门数据权限 <br>
     * version: 1.0 <br>
     * date: 2020/4/26 10:21 <br>
     * author: XJM <br>
     */
    public void getUserDataScope(Set<String> companyDataScope, Set<String> deptDataScope, HtSysUser htSysUser){
        // 如果不是超级管理员，则获取数据权限信息
        if(htSysUser.getUserType() < HtSysUserService.USER_TYPE_3){
            List<HtSysRole> htSysRoleList = htSysRoleService.findRoleOfUser(htSysUser.getId());
            QueryWrapper<HtSysOffice> queryWrapper = null;
            QueryWrapper<HtSysDataScope> htSysDataScopeQueryWrapper = null;
            List<HtSysOffice> officeList = null;
            List<HtSysDataScope> htSysDataScopeList = null;
            if(!htSysRoleList.isEmpty()){
                for (HtSysRole htSysRole : htSysRoleList) {
                    if(HtSysRoleService.IS_CTRL_DEPT.equals(htSysRole.getIsCtrl())){
                        deptDataScope.add(htSysUser.getDeptId());
                        queryWrapper = new QueryWrapper<>();
                        queryWrapper.select("id");
                        queryWrapper.eq("office_type", HtSysOfficeService.OFFICE_TYPE);
                        queryWrapper.likeRight("parent_ids", htSysUser.getDept().getParentIds() + htSysUser.getDeptId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
                        officeList = htSysOfficeService.list(queryWrapper);
                        if(!officeList.isEmpty()){
                            for (HtSysOffice htSysOffice : officeList) {
                                deptDataScope.add(htSysOffice.getId());
                            }
                        }
                    }else if(HtSysRoleService.IS_CTRL_COMPANY.equals(htSysRole.getIsCtrl())){
                        deptDataScope.add(htSysUser.getCompanyId());
                        queryWrapper = new QueryWrapper<>();
                        queryWrapper.select("id");
                        queryWrapper.eq("office_type", HtSysOfficeService.CORP_TYPE);
                        queryWrapper.likeRight("parent_ids", htSysUser.getDept().getParentIds() + htSysUser.getDeptId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
                        officeList = htSysOfficeService.list(queryWrapper);
                        if(!officeList.isEmpty()){
                            for (HtSysOffice htSysOffice : officeList) {
                                companyDataScope.add(htSysOffice.getId());
                            }
                        }
                    }else{
                        htSysDataScopeQueryWrapper = new QueryWrapper<>();
                        htSysDataScopeQueryWrapper.select("id", "office_id", "type");
                        htSysDataScopeQueryWrapper.eq("role_id", htSysRole.getId());
                        htSysDataScopeList = htSysDataScopeService.list(htSysDataScopeQueryWrapper);
                        if(!htSysDataScopeList.isEmpty()){
                            for (HtSysDataScope htSysDataScope : htSysDataScopeList) {
                                if(HtSysDataScopeService.DATA_SCOPE_COMAPNY.equals(htSysDataScope.getType())){
                                    companyDataScope.add(htSysDataScope.getOfficeId());
                                }else{
                                    deptDataScope.add(htSysDataScope.getOfficeId());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * description: 根据登录名获取登录人信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 17:00 <br>
     * author: XJM <br>
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HtSysUser htSysUser = htSysUserService.getByLoginCode(username);
        if(htSysUser == null){
            throw new CustomUsernameNotFoundException(USER_NAME_NOT_FOUND_ERROR_CODE, messageSource.getMessage("system.error.loginCodeNotExists", null, LocaleContextHolder.getLocale()));
        }
        if(SystemConstant.DISABLE.equals(htSysUser.getStatus())){
            throw new CustomUsernameNotFoundException(USER_DISABLE_ERROR_CODE, messageSource.getMessage("system.error.loginCodeDisabled", null, LocaleContextHolder.getLocale()));
        }
        List<HtSysMenu> permissions = new ArrayList<>();

        // 如果是超级管理员,则获取所有的权限字符串
        if(HtSysUserService.USER_TYPE_3 == htSysUser.getUserType()){
            permissions = htSysMenuService.findPermissionsOfUser(null);
        }else{
            permissions = htSysMenuService.findPermissionsOfUser(htSysUser.getId());
        }
        String[] roles = new String[permissions.size()];
        if(!permissions.isEmpty()){
            for (int i = 0; i < permissions.size(); i++) {
                roles[i] = permissions.get(i).getPermission();
            }
        }

        Token token = new Token();
        token.setPermissions(roles);
        token.setHtSysUser(htSysUser);
        CustomUser userDetails = (CustomUser) CustomUser.customBuilder().username(htSysUser.getLoginCode()).password(customPasswordEncoder.encode(htSysUser.getPassword())).roles(roles).build();
        userDetails.setToken(token);
        return userDetails;
    }
}
