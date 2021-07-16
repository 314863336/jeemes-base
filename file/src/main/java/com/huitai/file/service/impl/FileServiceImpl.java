package com.huitai.file.service.impl;

import com.huitai.file.entity.FileInfo;
import com.huitai.file.service.FileService;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * description: 文件服务接口实现
 * date: 2020/4/27 10:46
 * author: TYJ
 * version: 1.0
 */

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.location}")
    private String path;

    @Value("${file.imageAllowSuffixes}")
    private String imageType;

    @Value("${file.fileAllowSuffixes}")
    private String fileType;

    @Value("${file.mediaAllowSuffixes}")
    private String mediaType;

    /**
     * 注入转换器
     */
    @Autowired
    private DocumentConverter converter;

    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = new FileInfo();

        String fileName = file.getOriginalFilename();
//        int size = (int) file.getSize();

        LocalDate date = LocalDate.now();
        String uuid = UUID.randomUUID().toString();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(!Arrays.asList(imageType.split(",")).contains(suffix)
        && !Arrays.asList(fileType.split(",")).contains(suffix)
        && !Arrays.asList(mediaType.split(",")).contains(suffix)){
            throw new Exception("当前系统不支持该类型文件上传！");
        }
        String address = path + "/"+ date.getYear() + "/"+ date.getMonthValue() + "/"+ date.getDayOfMonth() + "/" + uuid + suffix;
        File dest = new File(address);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest); //保存文件
        fileInfo.setFileId(uuid);
        fileInfo.setFileName(fileName);
        fileInfo.setAddress(address);
        return fileInfo;
    }

    /**
     * description: 转换文件 <br>
     * version: 1.0 <br>
     * date: 2020/4/29 14:36 <br>
     * author: TYJ <br>
     */ 
    @Async
    @Override
    public void convert(File dest, String suffix, String uuid) throws OfficeException {
        //非图片、非视频、非PDF文件才进行转换
        if(!Arrays.asList(imageType.split(",")).contains(suffix)
                && !Arrays.asList(mediaType.split(",")).contains(suffix)
                && suffix.indexOf("pdf") == -1){
            if(suffix.indexOf("xls") >= 0){
                suffix = ".html";
            }else{
                suffix = ".pdf";
            }
            File newFile = new File(path+"/preview/"+uuid+suffix);
            converter.convert(dest).to(newFile).execute();
        }

    }

    /**
     * description: 删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:15 <br>
     * author: TYJ <br>
     */ 
    @Override
    public void deleteFile(String pathName){
        //删除本文件
        File target = new File(pathName);
        if(target.exists()){
            target.delete();
            //删除预览文件
            String suffix = pathName.substring(pathName.lastIndexOf("."));
            String name = pathName.substring(pathName.lastIndexOf("/")+1, pathName.lastIndexOf("."));
            if(suffix.indexOf("xls") >= 0){
                suffix = ".html";
            }else{
                suffix = ".pdf";
            }
            File previewTarget = new File(path+"/preview/" + name + suffix);
            if(previewTarget.exists()) {
                previewTarget.delete();
            }
        }
    }

    /**
     * description: 批量删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:25 <br>
     * author: TYJ <br>
     */
    @Override
    public void batchDeleteFile(List<String> pathNames){
        for(String pathName : pathNames){
            deleteFile(pathName);
        }
    }
}
