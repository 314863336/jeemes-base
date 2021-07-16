package com.huitai.core.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.AssignmentUtil;
import com.huitai.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description: BaseServiceImpl <br>
 * date: 2020/4/9 14:37 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    @Autowired
    protected MessageSource messageSource; //国际化操作
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;  //redis操作 存储字符串
    @Autowired
    protected RedisTemplate<String, Serializable> redisCacheTemplate;  //redis操作 存储对象

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    @Transactional(readOnly = false)
    @Override
    public boolean save(T entity) {

        if(entity != null){
            HtSysUser htSysUser = UserUtil.getCurUser();
            if(htSysUser != null){
                AssignmentUtil.setBaseFields(htSysUser, entity);
            }
            return SqlHelper.retBool(getBaseMapper().insert(entity));
        }
        return false;
    }

    /**
     * 修改一条记录
     *
     * @param entity 实体对象集合
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateById(T entity){
        AssignmentUtil.setUpdateFields(entity);
        return super.updateById(entity);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList) {
        HtSysUser htSysUser = UserUtil.getCurUser();
        for(T entity : entityList){
            if(entity != null){
                AssignmentUtil.setBaseFields(htSysUser, entity);
            }
        }
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    @DS("slave")
    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (throwEx) {
            return baseMapper.selectOne(queryWrapper);
        }
        return SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
    }

    @DS("slave")
    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
    }

    @DS("slave")
    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    @DS("slave")
    @Override
    public T getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    @DS("slave")
    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectBatchIds(idList);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    @DS("slave")
    @Override
    public List<T> listByMap(Map<String, Object> columnMap) {
        return getBaseMapper().selectByMap(columnMap);
    }

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return getOne(queryWrapper, true);
    }

    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    @DS("slave")
    @Override
    public int count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public int count(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(getBaseMapper().selectCount(queryWrapper));
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    @DS("slave")
    @Override
    public List<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectPage(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    @DS("slave")
    @Override
    public <E extends IPage<T>> E page(E page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMaps(queryWrapper);
    }

    /**
     * 查询所有列表
     *
     * @see Wrappers#emptyWrapper()
     */
    @DS("slave")
    @Override
    public List<Map<String, Object>> listMaps() {
        return listMaps(Wrappers.emptyWrapper());
    }

    /**
     * 查询全部记录
     */
    @DS("slave")
    @Override
    public List<Object> listObjs() {
        return listObjs(Function.identity());
    }

    /**
     * 查询全部记录
     *
     * @param mapper 转换函数
     */
    @DS("slave")
    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public List<Object> listObjs(Wrapper<T> queryWrapper) {
        return listObjs(queryWrapper, Function.identity());
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    @DS("slave")
    @Override
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return getBaseMapper().selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DS("slave")
    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMapsPage(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    @DS("slave")
    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return pageMaps(page, Wrappers.emptyWrapper());
    }

    /**
     * description: 验证字段是否已存在, fieldName：数据字段名 <br>
     * version: 1.0 <br>
     * date: 2020/4/30 10:06 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    public void checkExistsField(T t, String fieldName, String fieldValue, String msg){
        if(t == null || StringUtil.isBlank(fieldName) || StringUtil.isBlank(fieldValue)){
            return;
        }
        int count = 0;
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(fieldName, fieldValue);
        if(t instanceof BaseEntityD){
            if(StringUtil.isNotBlank(((BaseEntityD)t).getId())){
                queryWrapper.ne("id", ((BaseEntityD)t).getId());
            }
        }
        count = baseMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new BaseException(msg);
        }
    }
}
