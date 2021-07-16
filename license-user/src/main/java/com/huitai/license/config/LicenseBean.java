package com.huitai.license.config;

import de.schlichtherle.license.LicenseManager;

/**
 * description: LicenseBean <br>
 * date: 2020/5/13 9:46 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LicenseBean {
    private LicenseManager licenseManager;

    public LicenseManager getLicenseManager() {
        return licenseManager;
    }

    public void setLicenseManager(LicenseManager licenseManager) {
        this.licenseManager = licenseManager;
    }
}
