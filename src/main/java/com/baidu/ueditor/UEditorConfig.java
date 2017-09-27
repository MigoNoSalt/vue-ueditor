package com.baidu.ueditor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pangxiaofeng on 2017/9/27.
 */
@ConfigurationProperties(prefix = "ueditor")
public class UEditorConfig {

    /**
     * config.json的文件存放地址
     */
    private String config;
    /**
     * 是否同统一上传地址：图片上传地址，视频上传地址...
     */
    private boolean unified;
    /**
     * 文件上传路径
     */
    private String uploadPath;
    /**
     * 文件url前缀
     */
    private String urlPrefix;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public boolean getUnified() {
        return unified;
    }

    public void setUnified(boolean unified) {
        this.unified = unified;
    }
}
