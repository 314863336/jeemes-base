package com.huitai.core.tools.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huitai.core.tools.entity.HtGenTable;
import com.huitai.core.tools.entity.HtGenTableColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成信息表 Mapper 接口
 * </p>
 *
 * @author TYJ
 * @since 2020-05-20
 */
public interface HtGenTableDao extends BaseMapper<HtGenTable> {

    /**
     * description: 分页查询 <br>
     * version: 1.0 <br>
     * date: 2020/5/20 10:49 <br>
     * author: TYJ <br>
     */ 
    List<HtGenTable> selectPageByEntity(@Param("offset") long offset, @Param("size") long size, @Param("htGenTable") HtGenTable htGenTable);

    /**
     * description: 查询记录数 <br>
     * version: 1.0 <br>
     * date: 2020/5/20 10:49 <br>
     * author: TYJ <br>
     */ 
    long selectCountByEntity(@Param("htGenTable") HtGenTable htGenTable);

    /**
     * description: 获取数据库表信息 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 9:11 <br>
     * author: TYJ <br>
     */
    List<Map<String, Object>> selectTables(@Param("tableName") String tableName);
    
    /**
     * description: 获取表字段信息 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 11:10 <br>
     * author: TYJ <br>
     */ 
    List<HtGenTableColumn> selectColumns(@Param("tableName") String tableName);

    /**
     * description: 获取可选的父表 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 11:15 <br>
     * author: TYJ <br>
     */ 
    List<HtGenTable> selectParentTables(@Param("excludeName") String excludeName);
}
