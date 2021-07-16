package com.huitai.common.utils;

import java.util.UUID;

/**
 * description: 生成uuid <br>
 * date: 2020/4/23 14:23 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class GenerateUUIDUtil {
    /**
     * 生成UUID, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
