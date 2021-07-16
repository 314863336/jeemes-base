package com.huitai.core.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description: BaseEntity <br>
 * date: 2020/4/9 14:39 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public class BaseEntity extends BaseEntityD implements Serializable {

    private static final long serialVersionUID = -6707406290207423174L;

    @ApiModelProperty(value = "状态（0正常1停用）")
    @ExcelIgnore
    private String status;

    @ApiModelProperty(value = "备注信息")
    @ExcelIgnore
    private String remarks;

    @ApiModelProperty(value = "创建者主键")
    @ExcelIgnore
    private String createBy;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    @ExcelIgnore
    private Date createDate;

    @ApiModelProperty(value = "修改者主键")
    @ExcelIgnore
    private String updateBy;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    @ExcelIgnore
    private Date updateDate;

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
}
