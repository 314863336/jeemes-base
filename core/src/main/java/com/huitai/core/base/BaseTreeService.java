package com.huitai.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.global.SystemConstant;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * description: BaseServiceImpl <br>
 * date: 2020/4/9 14:37 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public abstract class BaseTreeService<M extends BaseMapper<T>, T extends BaseTreeEntity> extends BaseServiceImpl<M, T> {

    /**
     * description: 保存或修改时设置树的属性 <br>
     * version: 1.0 <br>
     * date: 2020/4/16 15:27 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void saveOrUpdateForTreeFields(T entity) {

        BaseTreeEntity oldEntity = baseMapper.selectById(entity.getId());

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 设置parentIds
        StringBuilder parentIds = new StringBuilder();
        if(StringUtil.isEmpty(entity.getParentId())){
            // 如果没有父节点，则父id设为0 SystemConstant.DEFAULT_PARENTID
            entity.setParentId(SystemConstant.DEFAULT_PARENTID);
            parentIds.append(SystemConstant.DEFAULT_PARENTID + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
        }else{
            BaseTreeEntity pEntity = baseMapper.selectById(entity.getParentId());
            if (pEntity != null) {
                parentIds.append(pEntity.getParentIds() + pEntity.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
            }else{
                parentIds.append(SystemConstant.DEFAULT_PARENTID + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
            }
        }
        entity.setParentIds(parentIds.toString());

        // 设置子数据的parentIds, 如果parentId不改变,则不修改子数据parentIds
        if(oldEntity != null && !oldEntity.getParentId().equals(entity.getParentId())) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.likeRight("parent_ids", oldEntity.getParentIds() + oldEntity.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
            List<T> list = baseMapper.selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                for (T t : list) {
                    t.setParentIds(t.getParentIds().replace(oldEntity.getParentIds(), entity.getParentIds()));
                    super.updateById(t);
                }
            }
        }

        // 设置treeLevel
        entity.setTreeLevel(entity.getParentIds().split(SystemConstant.DEFAULT_PARENTIDS_SPLIT).length);

        // 设置treeSort, 如果parentId不改变,则不修改treeSort
        if((oldEntity != null && !oldEntity.getParentId().equals(entity.getParentId())) || oldEntity == null){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.select("max(tree_sort) as treeSort");
            queryWrapper.eq("parent_id", entity.getParentId());
            BaseTreeEntity t = baseMapper.selectOne(queryWrapper);
            if(t != null){
                entity.setTreeSort(t.getTreeSort() + SystemConstant.DEFAULT_TREE_SORT_ADD);
            }else{
                entity.setTreeSort(SystemConstant.DEFAULT_TREE_SORT);
            }
        }

        // 设置treeLeaf,新增时不设置treeLeaf，这里自动设置为1
        if(StringUtil.isEmpty(entity.getTreeLeaf())){
            entity.setTreeLeaf(SystemConstant.YES);
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", entity.getParentId());
        T t = baseMapper.selectOne(queryWrapper);
        // 设置父数据treeLeaf
        if(t != null){
            t.setTreeLeaf(SystemConstant.NO);
            super.updateById(t);
        }

        if(oldEntity == null){
            super.save(entity);
        }else{
            super.updateById(entity);
        }

    }

    /**
     * description: 删除时修改父数据的treeLeaf字段, cascadDelete是否删除子数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/16 15:44 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    public void deleteForTreeFields(Serializable id, boolean cascadDelete) {
        T oldEntity = baseMapper.selectById(id);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", oldEntity.getParentId());
        T t = baseMapper.selectOne(queryWrapper);
        if (t != null) {
            t.setTreeLeaf(SystemConstant.YES);
            super.updateById(t);
        }
        baseMapper.deleteById(oldEntity.getId());
        if(cascadDelete){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.likeRight("parent_ids", oldEntity.getParentIds() + oldEntity.getId() + SystemConstant.DEFAULT_PARENTIDS_SPLIT);
            List<T> list = baseMapper.selectList(queryWrapper);
            if(list != null && list.size() > 0){
                for (T entity : list) {
                    baseMapper.deleteById(entity.getId());
                }
            }
        }
    }
}
