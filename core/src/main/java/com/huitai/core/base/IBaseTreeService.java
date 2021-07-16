package com.huitai.core.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * description: XXX
 * date: 2020/4/17 10:58
 * author: TYJ
 * version: 1.0
 */
public interface IBaseTreeService<T> extends IService<T> {

    /*
     * description: 保存树 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 10:53 <br>
     * author: TYJ <br>
     */
    void saveOrUpdateForTreeFields(T entity);

    /*
     * description: 删除树 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 10:58 <br>
     * author: TYJ <br>
     */
    void deleteForTreeFields(Serializable id, boolean cascadDelete);
}
