package com.huitai.core.system.entity;

import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 系统参数表
 * </p>
 *
 * @author TYJ
 * @since 2020-04-22
 */
@ApiModel(value="HtSysConfig对象", description="系统参数表")
public class HtSysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "姓名 不允许为空")
    @Length(max=100, message="名称长度不能超过 100 个字符")
    private String configName;

    private String configKey;

    private String configValue;

    private String status;

    private String remarks;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "HtSysConfig{" +
            "id=" + id +
            ", configName=" + configName +
            ", configKey=" + configKey +
            ", configValue=" + configValue +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }
}
