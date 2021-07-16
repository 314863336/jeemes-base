package com.huitai.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * description: Spring上下文工具类 <br>
 * date: 2020/4/15 16:50 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * description: 重写ApplicationContextAware接口的context注入函数 <br>
     * version: 1.0 <br>
     * date: 2020/4/15 17:13 <br>
     * author: PLF <br>
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * description: 获取ApplicationContext <br>
     * version: 1.0 <br>
     * date: 2020/4/15 17:14 <br>
     * author: PLF <br>
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * description: 清除applicationContext<br>
     * version: 1.0 <br>
     * date: 2020/4/15 17:20 <br>
     * author: PLF <br>
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    /**
     * description: 校验 <br>
     * version: 1.0 <br>
     * date: 2020/4/16 9:14 <br>
     * author: PLF <br>
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

    /**
     * description: 根据名称取得Bean, 并转型为所赋值对象的类型 <br>
     * version: 1.0 <br>
     * date: 2020/4/16 9:16 <br>
     * author: PLF <br>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * description: 根据类取得Bean, 并转型为所赋值对象的类型 <br>
     * version: 1.0 <br>
     * date: 2020/4/16 9:22 <br>
     * author: PLF <br>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return  applicationContext.getBean(clazz);
    }
}
