package com.huitai.license.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * description: 创建license的实体类 <br>
 * date: 2020/5/11 15:41 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LicenseCreator implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 证书生效时间
     */
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    private Date expiryTime;

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * mac 用户类型之类的验证
     */
    private LicenseChecker licenseChecker;


    public LicenseCreator(){

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(Date issuedTime) {
        this.issuedTime = issuedTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LicenseChecker getLicenseChecker() {
        return licenseChecker;
    }

    public void setLicenseChecker(LicenseChecker licenseChecker) {
        this.licenseChecker = licenseChecker;
    }
}
