package com.huitai.file.entity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * description: 文件信息
 * date: 2020/4/27 11:42
 * author: TYJ
 * version: 1.0
 */
public class FileInfo implements Serializable {

    private static final long serialVersionUID = -8939306243366229908L;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件存储地址
     */
    @NotBlank(message = "文件地址不能为空")
    private String address;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
