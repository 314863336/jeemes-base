package com.huitai.core.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.file.entity.HtFileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文档信息表 Mapper 接口
 * </p>
 *
 * @author TYJ
 * @since 2020-04-30
 */
public interface HtFileInfoDao extends BaseMapper<HtFileInfo> {

    /**
     * description: 分页获取文件列表 <br>
     * version: 1.0 <br>
     * date: 2020/5/18 11:14 <br>
     * author: TYJ <br>
     */ 
    List<HtFileInfo> findPage(@Param("offset")long offset, @Param("size")long size, @Param("htFileInfo")HtFileInfo htFileInfo);
    
    /**
     * description: 查询文件列表记录数 <br>
     * version: 1.0 <br>
     * date: 2020/5/18 11:41 <br>
     * author: TYJ <br>
     */ 
    long findTotal(@Param("htFileInfo")HtFileInfo htFileInfo);

    /**
     * description: 移动文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/25 11:51 <br>
     * author: TYJ <br>
     */
    void moveFile(@Param("target")String target, @Param("ids")String[] ids);
}
