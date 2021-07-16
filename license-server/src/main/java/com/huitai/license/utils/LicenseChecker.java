package com.huitai.license.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description: LicenseChecker <br>
 * date: 2020/5/11 15:50 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LicenseChecker implements Serializable {
    private static final long serialVersionUID = 8600137500316662317L;

    private String mac;
    private String userType;
    private Date startDate;
    private Date endDate;


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @JsonFormat(pattern="YYYY年MM月DD日")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern="YYYY年MM月DD日")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
