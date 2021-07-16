package com.huitai.core.system.service;

import com.huitai.core.system.entity.HtSysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-22
 */
public interface HtSysConfigService extends IService<HtSysConfig> {

    void update(HtSysConfig htSysConfig);

    List<Serializable> listRedis();

    String getValueByKey(String key);
}
