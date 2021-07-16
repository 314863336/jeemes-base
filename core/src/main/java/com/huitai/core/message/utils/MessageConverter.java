package com.huitai.core.message.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 消息转换 <br>
 * date: 2020/5/7 14:25 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class MessageConverter {
    // 默认使用该路径模板文件填充content
    public final String FTL_PATH_DEFAULT = "/email/htmlEmailTemplate.ftl";

    private String to;
    private String title;
    private String content;
    private List<String> filePaths;
    // 需要渲染时，map不可为空
    private Map<String, Object> map;
    // 是否需要模板文件填充content, 发送邮件时需要为true，系统消息不需要
    private boolean needFtl;

    public MessageConverter(){

    }

    // 带附件
    public MessageConverter(String to, String title, String content, Map<String, Object> map, boolean needFtl, List<String> filePaths){
        this.to = to;
        this.title = title;
        this.content = content;
        this.filePaths = filePaths;
        this.map = map;
        this.needFtl = needFtl;
        if(map != null){
            this.convert();
        }
    }

    // 不带附件
    public MessageConverter(String to, String title, String content, Map<String, Object> map, boolean needFtl){
        this.to = to;
        this.title = title;
        this.content = content;
        this.map = map;
        this.needFtl = needFtl;
        if(map != null){
            this.convert();
        }
    }

    // 不发邮件
    public MessageConverter(String title, String content, Map<String, Object> map, boolean needFtl){
        this.title = title;
        this.content = content;
        this.map = map;
        this.needFtl = needFtl;
        if(map != null){
            this.convert();
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public boolean isNeedFtl() {
        return needFtl;
    }

    public void setNeedFtl(boolean needFtl) {
        this.needFtl = needFtl;
    }

    public void convert(){
        if(needFtl){
            Map<String, Object> data = new HashMap<>();
            data.put("title", this.title);
            data.put("content", FreeMarkerUtil.convert(this.map, this.content));
            this.content = FreeMarkerUtil.convertByPath(data, FTL_PATH_DEFAULT);
        }else{
            this.content = FreeMarkerUtil.convert(this.map, this.content);
        }

    }
}
