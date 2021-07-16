package com.huitai.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.exception.SystemException;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.dao.HtSysDictTypeDao;
import com.huitai.core.system.entity.HtSysDictData;
import com.huitai.core.system.entity.HtSysDictType;
import com.huitai.core.system.service.HtSysDictDataService;
import com.huitai.core.system.service.HtSysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-20
 */
@Service
public class HtSysDictTypeServiceImpl extends BaseServiceImpl<HtSysDictTypeDao, HtSysDictType> implements HtSysDictTypeService {

    @Autowired
    private HtSysDictDataService htSysDictDataService;

    @Transactional
    @Override
    public void update(HtSysDictType htSysDictType) {

        HtSysDictType old = getById(htSysDictType.getId());

        QueryWrapper<HtSysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", htSysDictType.getDictCode());
        queryWrapper.ne("id", htSysDictType.getId());
        HtSysDictType get = getOne(queryWrapper);
        if(get != null){
            throw new SystemException(messageSource.getMessage("system.error.dict.dataExist", null, LocaleContextHolder.getLocale()));
        }else{
            updateById(htSysDictType);
            if(!old.getDictCode().equals(htSysDictType.getDictCode())){
                UpdateWrapper<HtSysDictData> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("dict_type", htSysDictType.getDictCode());
                updateWrapper.eq("dict_type", old.getDictCode());
                htSysDictDataService.update(updateWrapper);
            }
        }
    }

    @Transactional
    @Override
    public void delete(String id) {
        HtSysDictType htSysDictType = getById(id);
        removeById(id);
        QueryWrapper<HtSysDictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", htSysDictType.getDictCode());
        htSysDictDataService.remove(queryWrapper);
    }

    @Override
    public void refreshDictCache() {
        stringRedisTemplate.delete(SystemConstant.REDIS_DICT_KEY);//清除字典缓存
    }
}
