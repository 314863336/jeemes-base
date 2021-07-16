package com.huitai.core.base;

import io.swagger.annotations.ApiModelProperty;

/**
 * description: 数据权限业务需要继承的基类 <br>
 * date: 2020/4/30 9:12 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class BaseDataScopeEntity extends BaseEntity {

    @ApiModelProperty(value = "创建人所属公司ID")
    private String createrCompanyId;

    @ApiModelProperty(value = "创建人所属部门ID")
    private String createrDeptId;

    public String getCreaterCompanyId() {
        return createrCompanyId;
    }

    public void setCreaterCompanyId(String createrCompanyId) {
        this.createrCompanyId = createrCompanyId;
    }

    public String getCreaterDeptId() {
        return createrDeptId;
    }

    public void setCreaterDeptId(String createrDeptId) {
        this.createrDeptId = createrDeptId;
    }
}
