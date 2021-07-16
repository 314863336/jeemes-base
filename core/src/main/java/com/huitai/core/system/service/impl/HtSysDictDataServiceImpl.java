package com.huitai.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.common.config.CommonProperties;
import com.huitai.core.global.SystemConstant;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.system.dao.HtSysDictDataDao;
import com.huitai.core.system.entity.HtSysDictData;
import com.huitai.core.system.service.HtSysDictDataService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典子表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-20
 */
@Service
public class HtSysDictDataServiceImpl extends BaseServiceImpl<HtSysDictDataDao, HtSysDictData> implements HtSysDictDataService {

    @Override
    public List<Serializable> listRedis() {
        List<Serializable> list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_DICT_KEY, 0, -1);
        if(list.isEmpty()){
            QueryWrapper<HtSysDictData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", 0);
            List<HtSysDictData>  htSysDictDatas = list(queryWrapper);
            for(HtSysDictData htSysDictData : htSysDictDatas){
                redisCacheTemplate.opsForList().leftPush(SystemConstant.REDIS_DICT_KEY, htSysDictData);
            }
            CommonProperties commonProperties = SpringContextUtil.getBean("commonProperties");//获取配置对象
            redisCacheTemplate.expire(SystemConstant.REDIS_DICT_KEY, commonProperties.getData_dictionary_cache_expiration_time(), TimeUnit.SECONDS);
            list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_DICT_KEY, 0, -1);
        }
        return list;
    }
}
