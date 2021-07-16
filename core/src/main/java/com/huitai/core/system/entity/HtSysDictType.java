package com.huitai.core.system.entity;

import com.huitai.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author TYJ
 * @since 2020-04-20
 */
@ApiModel(value="HtSysDictType对象", description="数据字典表")
public class HtSysDictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String dictName;

    private String dictCode;

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
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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
        return "HtSysDictType{" +
            "id=" + id +
            ", dictName=" + dictName +
            ", dictCode=" + dictCode +
            ", status=" + status +
            ", remarks=" + remarks +
            ", createBy=" + createBy +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", updateDate=" + updateDate +
        "}";
    }
}
