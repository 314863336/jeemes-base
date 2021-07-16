package com.huitai.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * description: ServletUtil <br>
 * date: 2020/4/23 14:42 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class ServletUtil {
    // 获取requestPayload数据,一个请求只可调用一次
    public static JSONObject getRequestPayload(HttpServletRequest req) {
        String jsonString = "";
        InputStream in = null;
        try {
            in = req.getInputStream();
            jsonString = IOUtils.toString(in, StandardCharsets.UTF_8);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(jsonString)) {
            return JSONObject.parseObject(jsonString);
        }
        return new JSONObject();
    }
}
