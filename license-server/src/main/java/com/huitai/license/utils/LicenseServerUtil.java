package com.huitai.license.utils;

import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 * description: 生成license <br>
 * date: 2020/5/11 11:11 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Component
public class LicenseServerUtil {

    private static Logger logger = LoggerFactory.getLogger(LicenseServerUtil.class);

    /**
     * 证书路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * <p>生成License证书</p>
     */
    public boolean generateLicense(LicenseCreator param){

        try {
            LicenseManager licenseManager = new LicenseManager(initLicenseParam(param));
            LicenseContent licenseContent = initLicenseContent(param);
            File file = new File(licensePath + "/" + param.getLicenseChecker().getMac());
            if(!file.exists()){
                file.mkdirs();
            }
            licenseManager.store(licenseContent,new File(licensePath + "/" + param.getLicenseChecker().getMac() + "/" + LicenseConstant.LICENSE_FILE_NAME));
            return true;
        }catch (Exception e){
            logger.error(MessageFormat.format("证书生成失败：{0}",param),e);
            return false;
        }

    }

    /**
     * <p>初始化证书生成参数</p>
     */
    private LicenseParam initLicenseParam(LicenseCreator param){

        Preferences preferences = Preferences.userNodeForPackage(LicenseServerUtil.class);
        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(KeyConstant.KEYSTORE_PASSWORD);
        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(LicenseServerUtil.class
                , KeyConstant.PRIVATE_KEY_FILE_PATH
                , KeyConstant.PRIVATE_ALIAS
                , KeyConstant.KEYSTORE_PASSWORD
                , KeyConstant.KEY_PASSWORD);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,privateStoreParam
                ,cipherParam);
    }

    /**
     * <p>初始化证书内容信息对象</p>
     */
    private LicenseContent initLicenseContent(LicenseCreator param){

        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(LicenseConstant.DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(LicenseConstant.DEFAULT_HOLDER_AND_ISSUER);

        // 设置证书名称
        licenseContent.setSubject(param.getSubject());
        // 设置证书有效期
        licenseContent.setIssued(param.getIssuedTime());
        // 设置证书生效日期
        licenseContent.setNotBefore(param.getIssuedTime());
        // 设置证书失效日期
        licenseContent.setNotAfter(param.getExpiryTime());
        // 设置证书描述信息
        licenseContent.setInfo(param.getDescription());

        //设置证书扩展信息（对象 -- 额外的mac、userType、startDate等信息）
        licenseContent.setExtra(param.getLicenseChecker());
        return licenseContent;
    }

}
