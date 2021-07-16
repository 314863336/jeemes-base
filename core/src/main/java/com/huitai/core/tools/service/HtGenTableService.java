package com.huitai.core.tools.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huitai.core.base.BaseIService;
import com.huitai.core.tools.entity.HtGenTable;
import com.huitai.core.tools.entity.HtGenTableColumn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成信息表 服务类
 * </p>
 *
 * @author TYJ
 * @since 2020-05-20
 */
public interface HtGenTableService extends BaseIService<HtGenTable> {

    /**
     * description: 分页查询 <br>
     * version: 1.0 <br>
     * date: 2020/5/20 10:44 <br>
     * author: TYJ <br>
     */ 
    IPage<HtGenTable> pageList(Page<HtGenTable> page, HtGenTable htGenTable);

    /**
     * description: 获取数据库表 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 9:18 <br>
     * author: TYJ <br>
     */ 
    List<Map<String, Object>> getTables(String tableName);

    /**
     * description: 获取表字段信息 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 11:11 <br>
     * author: TYJ <br>
     */ 
    List<HtGenTableColumn> getColumns(String tableName);

    /**
     * description: 保存 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 19:05 <br>
     * author: TYJ <br>
     */ 
    void saveGenTable(HtGenTable htGenTable);
    
    /**
     * description: 修改 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 9:34 <br>
     * author: TYJ <br>
     */ 
    void updateGenTable(HtGenTable htGenTable);
    
    /**
     * description: 删除 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 10:38 <br>
     * author: TYJ <br>
     */ 
    void deleteGenTable(String id);

    /**
     * description: 获取可选的父表 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 11:15 <br>
     * author: TYJ <br>
     */ 
    List<HtGenTable> getParentTables(String excludeName);

    /**
     * description: 生成代码 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 15:01 <br>
     * author: TYJ <br>
     */ 
    void genCode(String id);
}
