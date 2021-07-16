package com.huitai.license.utils;

/**
 * description: 公钥私钥相关参数 <br>
 * date: 2020/5/11 14:45 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class KeyConstant {

    //私钥存放路径
    public static final String PRIVATE_KEY_FILE_PATH = "/privateKey.keystore";
    
    //私钥别名
    public static final String PRIVATE_ALIAS = "privateKey";

    //获取keystore所需的密码
    public static final String KEYSTORE_PASSWORD = "huitai2020";

    //获取私钥所需密码
    public static final String KEY_PASSWORD = "HuiTai2020";

    //公钥存放路径
    public static final String PUBLICKEY_FILE_PATH = "/publicKey.keystore";

    //公钥别名
    public static final String PUBLIC_ALIAS = "publicKey";
}
