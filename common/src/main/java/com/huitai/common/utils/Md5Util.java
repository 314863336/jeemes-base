package com.huitai.common.utils;

import org.springframework.util.DigestUtils;

/**
 * description: md5工具类 <br>
 * date: 2020/4/8 11:40 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class Md5Util {
    /**
     * description: 字符串转md5码 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 11:41 <br>
     * author: XJM <br>
     */
    public static String stringToMD5(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }
}
