package com.huitai.license.controller;

import com.huitai.common.model.Result;
import com.huitai.license.config.LicenseBean;
import com.huitai.license.utils.LicenseConstant;
import com.huitai.license.utils.LicenseUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * description: LicenseController <br>
 * date: 2020/5/11 15:22 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Api(value = "LicenseClientController接口")
@RequestMapping("/license")
@RestController
public class LicenseController {

    Logger logger = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    private LicenseUserUtil licenseUserUtil;

    @Autowired
    private LicenseBean licenseBean;

    /**
     * 证书路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * description: 获取证书内容 <br>
     * version: 1.0 <br>
     * date: 2020/5/12 18:30 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "getLicenseContent",notes="")
    @RequestMapping(value="/getLicenseContent",method = {RequestMethod.GET})
    public Result getLicenseContent(){
        return Result.ok(licenseUserUtil.getLicenseContent());
    }

    /**
     * description: 验证证书 <br>
     * version: 1.0 <br>
     * date: 2020/5/12 18:30 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "verify",notes="")
    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public Result verify(){
        return Result.ok(licenseUserUtil.verify());
    }

    /**
     * description: 上传许可证书 <br>
     * version: 1.0 <br>
     * date: 2020/5/13 9:32 <br>
     * author: XJM <br>
     */
    @ApiOperation(value = "uploadLicense",notes="")
    @RequestMapping(value="/uploadLicense",method = {RequestMethod.POST})
    public Result uploadLicense(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            if(!file.isEmpty()){
                try{
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(licensePath + "/" + LicenseConstant.LICENSE_FILE_NAME)));
                    stream.write(bytes);
                    stream.close();
                    // 重新设置LicenseManager
                    licenseBean.setLicenseManager(licenseUserUtil.install());
                } catch (Exception e){
                    logger.error("证书文件上传失败", e);
                    return Result.error();
                }
            }
        }
        return Result.ok();
    }
}
