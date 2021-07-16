package com.huitai.license.utils;

import javax.security.auth.x500.X500Principal;

/**
 * description: LicenseConstant <br>
 * date: 2020/5/11 16:00 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LicenseConstant {

    //公钥存放路径
    public static final String PUBLICKEY_FILE_PATH = "/publicKey.keystore";

    //公钥别名
    public static final String PUBLIC_ALIAS = "publicKey";

    //获取keystore所需的密码
    public static final String KEYSTORE_PASSWORD = "huitai2020";

    // 证书的发行者和主体字段信息
    public final static X500Principal DEFAULT_HOLDER_AND_ISSUER =
            new X500Principal("CN=慧泰智能, OU=慧泰智能, O=慧泰智能, L=南京, ST=江苏, C=CN");

    // 普通版本
    public static final String USER_ORDINARY = "ordinary";

    // 开发版本
    public static final String USER_DEVELOP = "develop";

    // 专业版本
    public static final String USER_PROFESSION = "profession";

    // license文件名字
    public static final String LICENSE_FILE_NAME = "license.lic";
}
