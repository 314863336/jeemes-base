package com.huitai.core.utils;

import com.huitai.common.utils.GenerateIdUtil;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseDataScopeEntity;
import com.huitai.core.base.BaseEntity;
import com.huitai.core.base.BaseEntityD;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.entity.HtSysUser;

import java.util.Date;

/**
 * description: 公共赋值
 * date: 2020/5/27 10:04
 * author: TYJ
 * version: 1.0
 */
public class AssignmentUtil {


    /**
     * description: 设置公共属性 <br>
     * version: 1.0 <br>
     * date: 2020/5/27 10:10 <br>
     * author: TYJ <br>
     */
    public static <T> void setBaseFields(HtSysUser htSysUser, T entity) {
        if(entity instanceof BaseDataScopeEntity){
            if(StringUtil.isNotBlank(htSysUser.getCompanyId()))((BaseDataScopeEntity) entity).setCreaterCompanyId(htSysUser.getCompanyId());
            if(StringUtil.isNotBlank(htSysUser.getDeptId()))((BaseDataScopeEntity) entity).setCreaterDeptId(htSysUser.getDeptId());
        }
        if(entity instanceof BaseEntityD){
            ((BaseEntityD)entity).setId(GenerateIdUtil.getId());
        }
        if(entity instanceof BaseEntity){
            ((BaseEntity)entity).setUpdateBy(htSysUser.getLoginCode());
            ((BaseEntity)entity).setUpdateDate(new Date());
            ((BaseEntity)entity).setCreateBy(htSysUser.getLoginCode());
            ((BaseEntity)entity).setCreateDate(new Date());
            ((BaseEntity)entity).setStatus(SystemConstant.ENABLE);
        }
    }

    /**
     * description: 设置更新者和更新时间 <br>
     * version: 1.0 <br>
     * date: 2020/5/27 10:12 <br>
     * author: TYJ <br>
     */
    public static <T> void setUpdateFields(T entity) {
        if(entity instanceof BaseEntity){
            HtSysUser htSysUser = UserUtil.getCurUser();
            if(htSysUser != null){
                ((BaseEntity)entity).setUpdateBy(htSysUser.getLoginCode());
                ((BaseEntity)entity).setUpdateDate(new Date());
            }
        }
    }
}
