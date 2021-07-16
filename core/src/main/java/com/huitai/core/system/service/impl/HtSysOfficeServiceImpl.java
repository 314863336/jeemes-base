package com.huitai.core.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.config.CommonProperties;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.base.BaseTreeService;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.system.dao.HtSysOfficeDao;
import com.huitai.core.system.entity.HtSysOffice;
import com.huitai.core.system.service.HtSysOfficeService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-15
 */
@Service
public class HtSysOfficeServiceImpl extends BaseTreeService<HtSysOfficeDao, HtSysOffice> implements HtSysOfficeService {


    @DS("slave")
    @Override
    public List<HtSysOffice> findOfficeList(HtSysOffice htSysOffice) {
        if(htSysOffice.getParams() == null){
            htSysOffice.setParams(new HashMap<>());
        }
        return baseMapper.findOfficeList(htSysOffice);
    }

    @DS("slave")
    @Override
    public List<HtSysOffice> treeData(HtSysOffice htSysOffice){
        Wrapper<HtSysOffice> queryWrapper = new QueryWrapper<>(htSysOffice);
        return baseMapper.selectList(queryWrapper);
    }

    @DS("slave")
    @Override
    public HtSysOffice detail(String id) {
        return baseMapper.getOfficeById(id);
    }

    @Override
    public List<Serializable> listRedis() {
        List<Serializable> list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_OFFICE_KEY, 0, -1);
        if(list.isEmpty()){
            QueryWrapper<HtSysOffice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", 0);
            List<HtSysOffice> htSysOffices = list(queryWrapper);
            for(HtSysOffice htSysOffice : htSysOffices){
                redisCacheTemplate.opsForList().leftPush(SystemConstant.REDIS_OFFICE_KEY, htSysOffice);
            }
            CommonProperties commonProperties = SpringContextUtil.getBean("commonProperties");//获取配置对象
            redisCacheTemplate.expire(SystemConstant.REDIS_OFFICE_KEY, commonProperties.getSystem_config_cache_expiration_time(), TimeUnit.SECONDS);
            list = redisCacheTemplate.opsForList().range(SystemConstant.REDIS_OFFICE_KEY, 0, -1);
        }
        return list;
    }

}
