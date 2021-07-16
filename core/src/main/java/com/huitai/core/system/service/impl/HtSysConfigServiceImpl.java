package com.huitai.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.common.config.CommonProperties;
import com.huitai.core.global.SystemConstant;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.exception.SystemException;
import com.huitai.core.system.dao.HtSysConfigDao;
import com.huitai.core.system.entity.HtSysConfig;
import com.huitai.core.system.service.HtSysConfigService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-22
 */
@Service
public class HtSysConfigServiceImpl extends BaseServiceImpl<HtSysConfigDao, HtSysConfig> implements HtSysConfigService {

    @Transactional
    @Override
    public void update(HtSysConfig htSysConfig) {
        QueryWrapper<HtSysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key", htSysConfig.getConfigKey());
        queryWrapper.ne("id", htSysConfig.getId());
        HtSysConfig get = getOne(queryWrapper);
        if(get != null){
            throw new SystemException(messageSource.getMessage("system.error.config.dataExist", null, LocaleContextHolder.getLocale()));
        }else{
            updateById(htSysConfig);
        }
    }

    @Override
    public List<Serializable> listRedis() {
        List<Serializable> list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_CONFIG_KEY, 0, -1);
        if(list.isEmpty()){
            QueryWrapper<HtSysConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", 0);
            List<HtSysConfig> htSysConfigs = list(queryWrapper);
            for(HtSysConfig htSysConfig : htSysConfigs){
                redisCacheTemplate.opsForList().leftPush(SystemConstant.REDIS_CONFIG_KEY, htSysConfig);
            }
            CommonProperties commonProperties = SpringContextUtil.getBean("commonProperties");//获取配置对象
            redisCacheTemplate.expire(SystemConstant.REDIS_CONFIG_KEY, commonProperties.getSystem_config_cache_expiration_time(), TimeUnit.SECONDS);
            list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_CONFIG_KEY, 0, -1);
        }
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public String getValueByKey(String key){
        List<Serializable> list = listRedis();
        HtSysConfig htSysConfig = null;
        for (int i = 0; i < list.size(); i++) {
            htSysConfig = (HtSysConfig)list.get(i);
            if(key.equals(htSysConfig.getConfigKey())){
                return htSysConfig.getConfigValue();
            }
        }
        return "";
    }
}
