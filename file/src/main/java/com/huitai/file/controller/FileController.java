package com.huitai.file.controller;

import com.huitai.file.entity.FileInfo;
import com.huitai.file.entity.Result;
import com.huitai.file.service.FileService;
import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Arrays;


/**
 * description: 文件服务controller
 * date: 2020/4/27 10:44
 * author: TYJ
 * version: 1.0
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${file.location}")
    private String path;

    /**
     * 注入转换器
     */
    @Autowired
    private DocumentConverter converter;

    @Autowired
    private FileService fileService;

    /**
     * description: 文件上传 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 16:04 <br>
     * author: TYJ <br>
     */ 
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(MultipartFile file){
        if(file == null || file.isEmpty()){
            return Result.error("文件不能为空");
        }
        FileInfo fileInfo = null;
        try {
            fileInfo =  fileService.upload(file); //保存文件
            fileService.convert(new File(fileInfo.getAddress()),
                    fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(".")),
                    fileInfo.getFileId());//异步转换文件
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件上传失败！");
        }
        return Result.ok(fileInfo);
    }

    /**
     * description: 文件下载 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 16:05 <br>
     * author: TYJ <br>
     */ 
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @Valid FileInfo fileInfo, @PathVariable String fileName) throws UnsupportedEncodingException{
        File file = new File(fileInfo.getAddress());
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(fileName,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * description: 图片预览 <br>
     * version: 1.0 <br>
     * date: 2020/4/28 10:41 <br>
     * author: TYJ <br>
     */
    @RequestMapping(value = "/previewImage", method = {RequestMethod.GET}, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> previewImage(String address) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(address);
        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }
    
    /**
     * description: pdf或图片预览 <br>
     * version: 1.0 <br>
     * date: 2020/4/28 11:40 <br>
     * author: TYJ <br>
     */
    @RequestMapping("/previewPdf")
    public void previewPdf(HttpServletResponse response, String address) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(address);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        response.setHeader("Content-Disposition",
                "inline; filename= file");
        outputStream.flush();
    }

    /**
     * description: 通用预览(不包括pdf) <br>
     * version: 1.0 <br>
     * date: 2020/4/28 14:51 <br>
     * author: TYJ <br>
     */ 
    @RequestMapping("/preview")
    public void preview(HttpServletResponse response, String address) throws Exception {
        String suffix = ".pdf";
        if(address.indexOf("xls") >= 0){
            suffix = ".html";
        }
        String uuid = address.substring(address.lastIndexOf("/")+1,address.lastIndexOf("."));
        File newFile = new File(path+"/preview/"+uuid+suffix);

        ServletOutputStream outputStream = response.getOutputStream();
        InputStream in = new FileInputStream(newFile);// 读取文件
        // copy文件
        int i = IOUtils.copy(in, outputStream);
        System.out.println(i);
        in.close();
        outputStream.close();
    }

    /**
     * description: 删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:18 <br>
     * author: TYJ <br>
     */ 
    @RequestMapping("/delete")
    public void delete(String address){
        fileService.deleteFile(address);
    }

    /**
     * description: 删除文件 <br>
     * version: 1.0 <br>
     * date: 2020/5/19 11:18 <br>
     * author: TYJ <br>
     */
    @RequestMapping("/batchDelete")
    public void batchDelete(String addrs){
        fileService.batchDeleteFile(Arrays.asList(addrs.split(",")));
    }
}
