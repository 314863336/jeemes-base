package com.huitai.license.utils;

import com.huitai.common.utils.DateUtil;
import com.huitai.common.utils.MacUtil;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.license.config.LicenseBean;
import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;

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
        try {
            LicenseManager lm = new LicenseManager(initLicenseParam(subject));
            // 先卸载证书
            lm.uninstall();
            // 再安装
            LicenseContent licenseContent = lm.install(new File(licensePath + "/" + LicenseConstant.LICENSE_FILE_NAME));
            LicenseChecker licenseChecker = (LicenseChecker)licenseContent.getExtra();
            String mac = MacUtil.getMacAddress();
            if(!mac.equals(licenseChecker.getMac())){
                logger.info("当前机器mac地址不对, 当前机器mac: " + mac + ", 证书mac: " + licenseChecker.getMac());
                return null;
            }
            if(LicenseConstant.USER_DEVELOP.equals(licenseChecker.getUserType())){
                Date endDate = DateUtil.addYears(licenseChecker.getStartDate(), 1);
                if(endDate.after(new Date())){
                    logger.info("开发版证书已过期, mac: " + licenseChecker.getMac());
                    return null;
                }
            }
            logger.info("证书安装成功");
            return lm;
        } catch (Exception e) {
            logger.info("证书不存在");
            return null;
        }
    }

    /**
     * <p>校验License证书, true 可以使用所有功能 false 限制使用个别功能</p>
     */
    public boolean verify() {
        LicenseBean licenseBean = SpringContextUtil.getBean("licenseBean");
        if(licenseBean.getLicenseManager() == null){
            return false;
        }
        LicenseManager licenseManager = licenseBean.getLicenseManager();
        try {
            LicenseContent licenseContent = licenseManager.verify();
            LicenseChecker licenseChecker = (LicenseChecker)licenseContent.getExtra();

            return LicenseConstant.USER_ORDINARY.equals(licenseChecker.getUserType()) ? false : true;
        } catch (Exception e) {
            logger.info("证书校验失败,找不到证书文件");
            return false;
        }
    }

    /**
     * <p>校验License证书, true 可以使用所有功能 false 限制使用个别功能</p>
     */
    public LicenseChecker getLicenseContent() {
        LicenseChecker licenseChecker = new LicenseChecker();
        licenseChecker.setUserType(LicenseConstant.USER_ORDINARY);
        LicenseBean licenseBean = SpringContextUtil.getBean("licenseBean");
        if(licenseBean.getLicenseManager() == null){
            licenseChecker.setMac(MacUtil.getMacAddress());
            return licenseChecker;
        }
        LicenseManager licenseManager = licenseBean.getLicenseManager();
        try {
            LicenseContent licenseContent = licenseManager.verify();
            licenseChecker = (LicenseChecker)licenseContent.getExtra();
            if(LicenseConstant.USER_DEVELOP.equals(licenseChecker.getUserType())){
                licenseChecker.setEndDate(DateUtil.addYears(licenseChecker.getStartDate(), 1));
            }
            return licenseChecker;
        } catch (Exception e) {
            logger.info("证书校验失败,找不到证书文件");
            licenseChecker.setMac(MacUtil.getMacAddress());
            return licenseChecker;
        }
    }

    /**
     * <p>初始化证书生成参数</p>
     *
     * @param subject License校验类需要的参数
     */
    private LicenseParam initLicenseParam(String subject) {

        Preferences preferences = Preferences.userNodeForPackage(LicenseUserUtil.class);
        CipherParam cipherParam = new DefaultCipherParam(KeyConstant.KEYSTORE_PASSWORD);
        KeyStoreParam publicStoreParam = new DefaultKeyStoreParam(LicenseUserUtil.class
                , KeyConstant.PUBLICKEY_FILE_PATH
                , KeyConstant.PUBLIC_ALIAS
                , KeyConstant.KEYSTORE_PASSWORD
                , null);

        return new DefaultLicenseParam(subject
                , preferences
                , publicStoreParam
                , cipherParam);
    }
}
