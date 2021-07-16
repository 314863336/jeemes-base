package com.huitai.core.message.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * description: FreeMarkerUtil <br>
 * date: 2020/5/6 16:16 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class FreeMarkerUtil {

    static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    // 生命周期内只初始化一次
    private static Configuration cfg = null;

    /**
     * description: 转换${}内容 <br>
     * version: 1.0 <br>
     * date: 2020/5/6 16:24 <br>
     * author: XJM <br>
     */
    public static String convert(Map<String, Object> map, String code){
        if(cfg == null){
            cfg = new Configuration(Configuration.VERSION_2_3_30);
        }
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("convertTemplate", code);
        cfg.setTemplateLoader(stringLoader);
        Template temp = null;
        try {
            temp = cfg.getTemplate("convertTemplate","utf-8");
            Writer out = new StringWriter(2048);
            temp.process(map, out);
            out.flush();
            return out.toString().replaceAll("[\\n\\r]", "");
        } catch (IOException | TemplateException e) {
            logger.error("渲染消息模板出错", e);
        }
        return null;
    }

    /**
     * description: 从项目类路径下找模板文件渲染 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 13:56 <br>
     * author: XJM <br>
     */
    public static String convertByPath(Map<String, Object> map, String relativePath){
        if(cfg == null){
            cfg = new Configuration(Configuration.VERSION_2_3_30);
        }
        cfg.setClassForTemplateLoading(FreeMarkerUtil.class, "/");//类路径
        try{
            Template template = cfg.getTemplate(relativePath, "UTF-8");
            StringWriter out = new StringWriter();
            template.process(map, out);
            return out.toString();
        }catch(Exception e){
            logger.error("转换模板文件出错,模板文件: " + relativePath, e);
        }
        return null;
    }
}
