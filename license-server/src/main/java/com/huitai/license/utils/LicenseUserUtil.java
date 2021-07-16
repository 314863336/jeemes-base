package com.huitai.license.utils;

import com.huitai.common.utils.MacUtil;
import de.schlichtherle.license.LicenseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: 客户服务器安装与验证license <br>
 * date: 2020/5/11 16:23 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Component
public class LicenseUserUtil {

    private static Logger logger = LoggerFactory.getLogger(LicenseUserUtil.class);

    /**
     * 证书路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 证书路径
     */
    @Value("${license.subject}")
    private String subject;

    /**
     * <p>安装License证书</p>
     */
    public LicenseManager install() {
        return null;
    }

    /**
     * <p>校验License证书, true 可以使用所有功能 false 限制使用个别功能</p>
     */
    public boolean verify() {
        return true;
    }

    /**
     * <p>校验License证书, true 可以使用所有功能 false 限制使用个别功能</p>
     */
    public LicenseChecker getLicenseContent() {
        LicenseChecker licenseChecker = new LicenseChecker();
        licenseChecker.setUserType(LicenseConstant.USER_PROFESSION);
        licenseChecker.setMac(MacUtil.getMacAddress());
        return licenseChecker;
    }
}
