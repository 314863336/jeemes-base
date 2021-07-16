package com.huitai.file.service;

import com.huitai.file.entity.FileInfo;
import org.jodconverter.office.OfficeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * description: 文件服务接口
 * date: 2020/4/27 10:45
 * author: TYJ
 * version: 1.0
 */
public interface FileService {

    /**
     * description: 上传附件 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 15:16 <br>
     * author: TYJ <br>
     */ 
    FileInfo upload(MultipartFile file) throws Exception;

    /**
     * description: 异步转换文件 <br>
     * version: 1.0 <br>
     * date: 2020/4/29 15:06 <br>
     * author: TYJ <br>
     */ 
    void convert(File dest, String suffix, String uuid) throws OfficeException;

    /**
     * description: 删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:16 <br>
     * author: TYJ <br>
     */ 
    void deleteFile(String pathName);

    /**
     * description: 批量删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:25 <br>
     * author: TYJ <br>
     */ 
    void batchDeleteFile(List<String> pathNames);
}
