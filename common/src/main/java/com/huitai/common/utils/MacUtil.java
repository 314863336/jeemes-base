package com.huitai.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * description: MacUtil <br>
 * date: 2020/5/11 11:19 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class MacUtil {

    static Logger logger = LoggerFactory.getLogger(MacUtil.class);

    // 获取本机mac地址
    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("MAC地址获取失败", e);
        }
        return "";
    }

//    public static void main(String[] args) {
//        System.out.println(getMacAddress());
//    }
}
