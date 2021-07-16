package com.huitai.core.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * description: 子表基类 <br>
 * version: 1.0 <br>
 * date: 2020/5/27 9:47 <br>
 * author: TYJ <br>
 */ 
public class BaseEntityD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(value = "主键")
    @ExcelIgnore
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
