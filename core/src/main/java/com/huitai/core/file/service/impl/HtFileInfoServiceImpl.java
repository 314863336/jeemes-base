package com.huitai.core.file.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.file.dao.HtFileInfoDao;
import com.huitai.core.file.entity.HtFileInfo;
import com.huitai.core.file.service.HtFileInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文档信息表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-30
 */
@Service
public class HtFileInfoServiceImpl extends BaseServiceImpl<HtFileInfoDao, HtFileInfo> implements HtFileInfoService {

    /**
     * description: 分页获取文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/18 11:02 <br>
     * author: TYJ <br>
     */
    @DS("slave")
    @Override
    public IPage<HtFileInfo> findPage(Page<HtFileInfo> page, HtFileInfo htFileInfo) {
        page.setRecords(baseMapper.findPage((page.getCurrent()-1)*page.getSize(), page.getSize(), htFileInfo));
        page.setTotal(baseMapper.findTotal(htFileInfo));
        return page;
    }

    /**
     * description: 批量删除 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:07 <br>
     * author: TYJ <br>
     */ 
    @Transactional
    @Override
    public void batchDelete(List<HtFileInfo> htFileInfos) {
        if(htFileInfos != null && htFileInfos.size() > 0){
            List<String> list = new ArrayList<>();
            for (HtFileInfo htFileInfo : htFileInfos){
                list.add(htFileInfo.getId());
            }
            baseMapper.deleteBatchIds(list);
        }
    }

    /**
     * description: 移动文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/25 11:45 <br>
     * author: TYJ <br>
     */
    @Override
    public void moveFile(String target, String[] ids) {
        if(StringUtil.isNotBlank(target) && ids != null && ids.length > 0){
            baseMapper.moveFile(target, ids);
        }
    }
}
