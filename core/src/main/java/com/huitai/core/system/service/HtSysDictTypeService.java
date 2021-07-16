package com.huitai.core.system.service;

import com.huitai.core.system.entity.HtSysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-20
 */
public interface HtSysDictTypeService extends IService<HtSysDictType> {

    void update(HtSysDictType htSysDictType);

    void delete(String id);

    void refreshDictCache();
}
