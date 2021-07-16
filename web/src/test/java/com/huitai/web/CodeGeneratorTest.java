package com.huitai.web;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.huitai.common.config.querys.JeemesMySqlQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 代码生成器
 * date: 2020/4/8 9:39
 * author: TYJ
 * version: 1.0
 */
public class CodeGeneratorTest {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\huitai_git\\jeemes\\jeemes\\core\\src\\main\\java");//输出文件路径
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setDateType(DateType.ONLY_DATE);
        gc.setAuthor("TYJ");// 作者

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
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://168.168.8.174:3306/jeemes?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC");
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//        	@Override
//        	public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//        		System.out.println("转换类型：" + fieldType);
//        		//将数据库中datetime转换成date
//        		if ( fieldType.toLowerCase().contains( "datetime" ) ) {
//        			return DbColumnType.DATE;
//        		}
//        		return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
//        	}
//        });
        dsc.setDbQuery(new JeemesMySqlQuery());
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "ht_file_info" }); // 需要生成的表
        strategy.setSuperControllerClass("com.huitai.core.base.BaseController");
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass("com.huitai.core.base.BaseService");
        strategy.setSuperMapperClass(null);
        strategy.setSuperEntityClass("com.huitai.core.base.BaseEntity");
        // 生成 RestController 风格
        strategy.setRestControllerStyle(true);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig java = new PackageConfig();
        java.setParent("com.huitai.core.file");
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
                List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("childName", "TestDataChild1");
                map1.put("childname", "testDataChild1");
                childList.add(map1);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("childName", "TestDataChild2");
                map2.put("childname", "testDataChild2");
                childList.add(map2);
                map.put("childs", childList);
                //当前时间
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                map.put("time", LocalDateTime.now().format(formatter));

//                String s = "test_data_child";
                this.setMap(map);
            }
        };

        // 调整 xml 生成目录演示
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "D:\\huitai_git\\jeemes\\jeemes\\core\\src\\main\\resources"+ "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setController("/templates/controller.java");
        tc.setServiceImpl("/templates/serviceImpl.java");
        tc.setEntity("/templates/entity.java");
        mpg.setTemplate(tc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行生成java代码
        mpg.execute();


    }
}
