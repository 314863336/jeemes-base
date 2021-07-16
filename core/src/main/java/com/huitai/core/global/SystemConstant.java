package com.huitai.core.global;

/**
 * description: 系统常量 <br>
 * date: 2020/4/8 11:43 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class SystemConstant {

    public static final String REDIS_DICT_KEY = "redis:base:dict:key";

    public static final String REDIS_CONFIG_KEY = "redis:config:key";

    public static final String REDIS_OFFICE_KEY = "redis:office:key";

    // 用户登录常用ip key
    public static final String REDIS_USER_IP_KEY = "redis:user:ip:key";

    // 用户登录密码错误 key
    public static final String REDIS_USER_PASSWORD_ERROR_KEY = "redis:user:error:key";

    // 用户验证码 key
    public static final String REDIS_USER_CAPTCHA_KEY = "redis:user:captcha:key";

    // 用户存储redis key
    public static final String REDIS_USER_LOGIN_KEY = "redis:user:login:key";

    // socket存储redis key
    public static final String REDIS_SOCKET_KEY = "redis:socket:key";

    //初始文件夹名称
    public static final String ORIGIN_FOLDER_NAME = "文件管理";

    // 文件夹
    public static final String FILE_TYPE_FOLDER = "1";

    // 文件
    public static final String FILE_TYPE_ATTACHMENT = "2";

    // 启用
    public static final String ENABLE = "0";

    // 停用
    public static final String DISABLE = "1";

    // 是
    public static final String YES = "0";

    // 否
    public static final String NO = "1";

    // 默认parentId
    public static final String DEFAULT_PARENTID = "0";

    // 默认parentIds分割符
    public static final String DEFAULT_PARENTIDS_SPLIT = ",";

    // 默认的初级树表排序
    public static final int DEFAULT_TREE_SORT = 10;

    // 默认的树表排序增量
    public static final int DEFAULT_TREE_SORT_ADD = 10;
}
