package com.huitai.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.system.entity.HtSysDictData;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据字典子表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-20
 */
public interface HtSysDictDataService extends IService<HtSysDictData> {

    List<Serializable> listRedis();
}
