package com.huitai.license.config;

import com.huitai.license.utils.LicenseUserUtil;
import de.schlichtherle.license.LicenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: LicenseManagerConfig <br>
 * date: 2020/5/12 15:02 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Configuration
public class LicenseManagerConfig {

    @Autowired
    private LicenseUserUtil licenseUserUtil;

    /**
     * description: 初始化时安装证书 <br>
     * version: 1.0 <br>
     * date: 2020/5/13 8:40 <br>
     * author: XJM <br>
     */
    @Bean
    public LicenseBean licenseBean(){
        LicenseBean licenseBean = new LicenseBean();

        LicenseManager licenseManager = licenseUserUtil.install();
        licenseBean.setLicenseManager(licenseManager);
        return licenseBean;
    }
}
