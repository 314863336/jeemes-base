package com.huitai.core.file.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huitai.core.file.entity.HtFileInfo;

import java.util.List;

/**
 * <p>
 * 文档信息表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-04-30
 */
public interface HtFileInfoService extends IService<HtFileInfo> {
    
    /**
     * description: 分页获取文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/18 11:02 <br>
     * author: TYJ <br>
     */
    IPage<HtFileInfo> findPage(Page<HtFileInfo> page, HtFileInfo htFileInfo);

    /**
     * description: 批量删除 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:45 <br>
     * author: TYJ <br>
     */ 
    void batchDelete(List<HtFileInfo> htFileInfos);

    /**
     * description: 移动文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/25 11:45 <br>
     * author: TYJ <br>
     */ 
    void moveFile(String target, String[] ids);
}
