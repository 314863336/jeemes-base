package com.huitai.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseException;
import com.huitai.core.base.BaseTreeService;
import com.huitai.core.global.SystemConstant;
import com.huitai.core.system.dao.HtSysPostDao;
import com.huitai.core.system.entity.HtSysPost;
import com.huitai.core.system.entity.HtSysPostRole;
import com.huitai.core.system.entity.HtSysUserPost;
import com.huitai.core.system.service.HtSysPostRoleService;
import com.huitai.core.system.service.HtSysPostService;
import com.huitai.core.system.service.HtSysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author XJM
 * @since 2020-04-16
 */
@Service
public class HtSysPostServiceImpl extends BaseTreeService<HtSysPostDao, HtSysPost> implements HtSysPostService {

    @Autowired
    private HtSysPostRoleService htSysPostRoleService;

    @Autowired
    private HtSysUserPostService htSysUserPostService;

    /**
     * description: 保存列表排序 <br>
     * version: 1.0 <br>
     * date: 2020/4/17 16:45 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveTreeSortData(List<HtSysPost> htSysPostList){
        List<String> ids = new ArrayList<>();
        List<Integer> treeSorts = new ArrayList<>();
        for (int i = 0; i < htSysPostList.size(); i++) {
            ids.add(htSysPostList.get(i).getId());
            treeSorts.add(htSysPostList.get(i).getTreeSort());
        }
        List<HtSysPost> list = baseMapper.selectBatchIds(ids);
        for (int i = 0; i < list.size(); i++) {
            if(ids.contains(list.get(i).getId())){
                list.get(i).setTreeSort(treeSorts.get(ids.indexOf(list.get(i).getId())));
                baseMapper.updateById(list.get(i));
            }
        }
    }

    /**
     * description: 通过id获取岗位对象,包括父岗位信息 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 9:30 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = true)
    @Override
    public HtSysPost getPostById(String id){
        return baseMapper.getPostById(id);
    }

    /**
     * description: 修改岗位数据 <br>
     * version: 1.0 <br>
     * date: 2020/4/20 9:35 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdateHtSysPost(HtSysPost htSysPost){
        // 判断子岗位权重不可以小于父岗位权重
        if(!SystemConstant.DEFAULT_PARENTID.equals(htSysPost.getParentId())){
            HtSysPost pHtSysPost = super.getById(htSysPost.getParentId());
            if(pHtSysPost != null){
                if(htSysPost.getPostType() > pHtSysPost.getPostType()){
                    throw new BaseException(messageSource.getMessage("system.error.postWeightException", null, LocaleContextHolder.getLocale()));
                }
            }

        }
        // 验证编号是否存在
        checkExistsField(htSysPost, "post_code", htSysPost.getPostCode(), messageSource.getMessage("system.error.existsPostCode", null, LocaleContextHolder.getLocale()));
        super.saveOrUpdateForTreeFields(htSysPost);
    }

    /**
     * description: 删除岗位 <br>
     * version: 1.0 <br>
     * date: 2020/4/22 17:22 <br>
     * author: XJM <br>
     */
    @Transactional(readOnly = false)
    @Override
    public void deletePost(String[] ids){
        QueryWrapper<HtSysPostRole> queryWrapper = null;
        QueryWrapper<HtSysUserPost> queryWrapperU = null;
        for (int i = 0; i < ids.length; i++) {
            if(!StringUtil.isEmpty(ids[i])) {
                super.deleteForTreeFields(ids[i], true);
                // 删除角色岗位关联表数据
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("post_id", ids[i]);
                htSysPostRoleService.remove(queryWrapper);
                // 删除岗位用户关联表数据
                queryWrapperU = new QueryWrapper();
                queryWrapperU.eq("post_id", ids[i]);
                htSysUserPostService.remove(queryWrapperU);
            }
        }
    }
}
