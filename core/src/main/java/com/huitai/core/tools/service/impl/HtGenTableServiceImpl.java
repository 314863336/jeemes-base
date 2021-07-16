package com.huitai.core.tools.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.huitai.common.config.querys.JeemesMySqlQuery;
import com.huitai.common.config.querys.JeemesOracleQuery;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.base.BaseServiceImpl;
import com.huitai.core.tools.dao.HtGenTableDao;
import com.huitai.core.tools.entity.HtGenTable;
import com.huitai.core.tools.entity.HtGenTableColumn;
import com.huitai.core.tools.service.HtGenTableColumnService;
import com.huitai.core.tools.service.HtGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 代码生成信息表 服务实现类
 * </p>
 *
 * @author TYJ
 * @since 2020-05-20
 */
@Service
public class HtGenTableServiceImpl extends BaseServiceImpl<HtGenTableDao, HtGenTable> implements HtGenTableService {

    @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.dynamic.datasource.master.username}")
    private String userName;

    @Value("${spring.datasource.dynamic.datasource.master.password}")
    private String password;

    @Value("${spring.datasource.dynamic.datasource.master.url}")
    private String url;

    @Autowired
    private HtGenTableColumnService htGenTableColumnService;

    /**
     * description: 分页查询 <br>
     * version: 1.0 <br>
     * date: 2020/5/20 10:48 <br>
     * author: TYJ <br>
     */
    @DS("slave")
    @Override
    public IPage<HtGenTable> pageList(Page<HtGenTable> page, HtGenTable htGenTable) {
        page.setRecords(baseMapper.selectPageByEntity((page.getCurrent()-1) * page.getSize(), page.getSize(), htGenTable));
        page.setTotal(baseMapper.selectCountByEntity(htGenTable));
        return page;
    }

    /**
     * description: 获取数据库表 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 9:19 <br>
     * author: TYJ <br>
     */
    @DS("slave")
    @Override
    public List<Map<String, Object>> getTables(String tableName) {
        return baseMapper.selectTables(tableName);
    }

    /**
     * description: 获取表字段信息 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 11:11 <br>
     * author: TYJ <br>
     */
    @DS("slave")
    @Override
    public List<HtGenTableColumn> getColumns(String tableName) {
        List<HtGenTableColumn> list = baseMapper.selectColumns(tableName);
        ITypeConvert sqlTypeConvert = null;
        if(driverName.contains("mysql")){
            sqlTypeConvert = new MySqlTypeConvert();
        }
        if(driverName.contains("oracle")){
            sqlTypeConvert = new OracleTypeConvert();
        }
        if(sqlTypeConvert != null){
            for(HtGenTableColumn htGenTableColumn : list){
                htGenTableColumn.setAttrType(sqlTypeConvert.processTypeConvert(new GlobalConfig(), htGenTableColumn.getColumnType()).getType());
                htGenTableColumn.setAttrName(NamingStrategy.underlineToCamel(htGenTableColumn.getColumnName()));
                htGenTableColumn.setShowType("input");
            }
        }
        return list;
    }

    /**
     * description: 保存自动生成配置 <br>
     * version: 1.0 <br>
     * date: 2020/5/21 19:07 <br>
     * author: TYJ <br>
     */
    @Transactional
    @Override
    public void saveGenTable(HtGenTable htGenTable) {
        super.save(htGenTable);
        if(htGenTable.getHtGenTableColumns() != null && htGenTable.getHtGenTableColumns().size() > 0){
            for(HtGenTableColumn htGenTableColumn : htGenTable.getHtGenTableColumns()){
                htGenTableColumn.setTableName(htGenTable.getTableName());
            }
            htGenTableColumnService.saveBatch(htGenTable.getHtGenTableColumns());
        }
    }

    /**
     * description: 修改 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 9:34 <br>
     * author: TYJ <br>
     */
    @Transactional
    @Override
    public void updateGenTable(HtGenTable htGenTable) {
        super.updateById(htGenTable);
        if(htGenTable.getHtGenTableColumns() != null && htGenTable.getHtGenTableColumns().size() > 0){
            for(HtGenTableColumn htGenTableColumn : htGenTable.getHtGenTableColumns()){
                htGenTableColumnService.updateById(htGenTableColumn);
            }
        }
    }

    /**
     * description: 删除 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 10:39 <br>
     * author: TYJ <br>
     */
    @Transactional
    @Override
    public void deleteGenTable(String id) {
        HtGenTable htGenTable = getById(id);
        super.removeById(id);
        if(htGenTable != null && StringUtil.isNotBlank(htGenTable.getTableName())){
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("table_name", htGenTable.getTableName());
            htGenTableColumnService.removeByMap(columnMap);
        }
    }

    /**
     * description: 获取可选的父表 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 11:16 <br>
     * author: TYJ <br>
     */
    @DS("slave")
    @Override
    public List<HtGenTable> getParentTables(String excludeName) {
        return baseMapper.selectParentTables(excludeName);
    }

    /**
     * description: 生成代码 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 15:01 <br>
     * author: TYJ <br>
     */
    @Override
    public void genCode(String id) {
        HtGenTable htGenTable = getById(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_table_name", htGenTable.getTableName());
        //查询子表
        List<HtGenTable> list = list(queryWrapper);
        //添加主表
        list.add(htGenTable);
        String[] tableNames = new String[list.size()];
        //准备子表转化名比如：test_data => testData,TestData
        List<Map<String, String>> subTables = new ArrayList<>();
        //主表
        Map<String, String> hostTable = new HashMap<>();
        Map<String, String> map;
        for(int i = 0; i<list.size(); i++){
            String entityName = NamingStrategy.underlineToCamel(list.get(i).getTableName());
            String fkName = NamingStrategy.underlineToCamel(list.get(i).getTableFkName());
            String parentFkName = NamingStrategy.underlineToCamel(list.get(i).getParentTableFkName());
            if(i!=list.size()-1){
                map = new HashMap<>();
                map.put("entityName", entityName);
                map.put("EntityName", NamingStrategy.capitalFirst(entityName));
                map.put("fk_name", list.get(i).getTableFkName());
                map.put("fkName", fkName);
                map.put("FkName", NamingStrategy.capitalFirst(fkName));
                map.put("parent_fk_name", list.get(i).getParentTableFkName());
                map.put("parentFkName", parentFkName);
                map.put("ParentFkName", NamingStrategy.capitalFirst(parentFkName));
                subTables.add(map);
            }else{
                hostTable.put("entityName", entityName);
                hostTable.put("EntityName", NamingStrategy.capitalFirst(entityName));
            }
        }
        //获取需要生成的所有表名
        tableNames = list.stream().map(p->p.getTableName()).collect(Collectors.toList()).toArray(tableNames);
        excuteGenCode(htGenTable.getCodeBaseDir(),
                htGenTable.getFunctionAuthor(),
                tableNames,
                htGenTable.getPackageName(),
                htGenTable.getModuleName(),
                subTables,
                hostTable,
                htGenTable.getTplCategory());
    }

    /**
     * description: 生成代码执行 <br>
     * version: 1.0 <br>
     * date: 2020/5/22 16:03 <br>
     * author: TYJ <br>
     */
    public void excuteGenCode(String outputDir, String author, String[] tables, String packageName, String moduleName,
                              List<Map<String, String>> subTables, Map<String, String> hostTable, String tplCategory){
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir+"\\src\\main\\java");//输出文件路径
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setDateType(DateType.ONLY_DATE);
        gc.setAuthor(author);// 作者

        gc.setSwagger2(true);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        if(driverName.contains("mysql")){
            dsc.setDbType(DbType.MYSQL);
        }
        if(driverName.contains("oracle")){
            dsc.setDbType(DbType.ORACLE);
        }
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        dsc.setUrl(url);
        if(driverName.contains("mysql")){
            dsc.setDbQuery(new JeemesMySqlQuery());
        }
        if(driverName.contains("oracle")){
            dsc.setDbQuery(new JeemesOracleQuery());
        }
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setSuperControllerClass("com.huitai.core.base.BaseController");
        if("flw".equals(tplCategory)){
            strategy.setSuperServiceClass("com.huitai.bpm.manage.service.FlwBaseService");
            strategy.setSuperServiceImplClass("com.huitai.bpm.manage.service.impl.FlwBaseServiceImpl");
            strategy.setSuperMapperClass("com.huitai.bpm.examples.dao.FlwBaseMapper");
            strategy.setSuperEntityClass("com.huitai.bpm.manage.entity.FlwBaseEntity");
        }else {
            strategy.setSuperServiceClass("com.huitai.core.base.BaseIService");
            strategy.setSuperServiceImplClass("com.huitai.core.base.BaseServiceImpl");
            strategy.setSuperMapperClass(null);
            strategy.setSuperEntityClass("com.huitai.core.base.BaseEntity");
        }
        // 生成 RestController 风格
        strategy.setRestControllerStyle(true);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig java = new PackageConfig();
        java.setParent(packageName+"."+moduleName);
        java.setController("controller");
        java.setService("service");
        java.setServiceImpl("service.impl");
        java.setMapper("dao");
        java.setEntity("entity");
        mpg.setPackageInfo(java);


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-rb");
                map.put("subTables", subTables);//子表
                map.put("hostTable", hostTable);//主表
                //当前时间
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                map.put("time", LocalDateTime.now().format(formatter));
                if(driverName.contains("mysql")){
                    map.put("dbType", "mysql");
                }
                if(driverName.contains("oracle")){
                    map.put("dbType", "oracle");
                }

//                String s = "test_data_child";
                this.setMap(map);
            }
        };

        // 调整 xml 生成目录演示
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/" + tplCategory + "/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + "\\src\\main\\resources" + "/mapper/"
                        + moduleName + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setController("/templates/" + tplCategory + "/controller.java");
        tc.setService("/templates/" + tplCategory + "/service.java");
        tc.setServiceImpl("/templates/" + tplCategory + "/serviceImpl.java");
        tc.setEntity("/templates/" + tplCategory + "/entity.java");
        mpg.setTemplate(tc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行生成java代码
        mpg.execute();
    }


}
