package com.huitai.core.system.utils;

import com.huitai.common.utils.StringUtil;
import com.huitai.core.system.entity.HtSysDictData;
import com.huitai.core.system.service.HtSysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * description: 数据字典工具类
 * date: 2020/9/7 13:39
 * author: TYJ
 * version: 1.0
 */
@Component
public class DictUtil {

    @Autowired
    private HtSysDictDataService htSysDictDataService;


    /**
     * 根据字典值获取标签
     * @param dictType
     * @param value
     * @return
     */
    public String getLabelByValue(String dictType, String value){
        if(StringUtil.isNotBlank(dictType) && StringUtil.isNotBlank(value)){
            List<Serializable> list = htSysDictDataService.listRedis();
            HtSysDictData htSysDictData = null;
            for(Serializable ser : list){
                htSysDictData = (HtSysDictData)ser;
                if(dictType.equals(htSysDictData.getDictType()) && value.equals(htSysDictData.getDictValue())){
                    return htSysDictData.getDictLabel();
                }
            }
        }
        return null;
    }
}
