package com.baidu.ueditor;

import com.baidu.ueditor.define.ActionMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 配置管理器
 *
 * @author hancong03@baidu.com
 */
public final class ConfigManager {

    private static final String configFileName = "config.json";
    private JSONObject jsonConfig = null;
    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String REMOTE_FILE_NAME = "remote";
    //配置信息
    private UEditorConfig uEditorConfig;

    /*
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    private ConfigManager(UEditorConfig uEditorConfig) throws IOException {
        this.uEditorConfig = uEditorConfig;
        String configPath = uEditorConfig.getConfig();
        configPath = configPath == null || configPath.isEmpty() ? configFileName : configPath;
        this.initEnv(configPath);
    }

    /**
     * 配置管理器构造工厂
     *
     * @param uEditorConfig 配置文件
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(UEditorConfig uEditorConfig) {

        try {
            return new ConfigManager(uEditorConfig);
        } catch (Exception e) {
            System.err.println("UEditor ConfigManager load error~");
            return null;
        }

    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {

        return this.jsonConfig;

    }

    public Map<String, Object> getConfig(int type) {

        Map<String, Object> conf = new HashMap<String, Object>();
        String savePath = null;

        try {
            switch (type) {

                case ActionMap.UPLOAD_FILE:
                    conf.put("isBase64", "false");
                    conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
                    conf.put("allowFiles", this.getArray("fileAllowFiles"));
                    conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
                    savePath = this.jsonConfig.getString("filePathFormat");
                    break;

                case ActionMap.UPLOAD_IMAGE:
                    conf.put("isBase64", "false");
                    conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
                    conf.put("allowFiles", this.getArray("imageAllowFiles"));
                    conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
                    savePath = this.jsonConfig.getString("imagePathFormat");
                    break;

                case ActionMap.UPLOAD_VIDEO:
                    conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
                    conf.put("allowFiles", this.getArray("videoAllowFiles"));
                    conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
                    savePath = this.jsonConfig.getString("videoPathFormat");
                    break;

                case ActionMap.UPLOAD_SCRAWL:
                    conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                    conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
                    conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
                    conf.put("isBase64", "true");
                    savePath = this.jsonConfig.getString("scrawlPathFormat");
                    break;

                case ActionMap.CATCH_IMAGE:
                    conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                    conf.put("filter", this.getArray("catcherLocalDomain"));
                    conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
                    conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                    conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
                    savePath = this.jsonConfig.getString("catcherPathFormat");
                    break;

                case ActionMap.LIST_IMAGE:
                    conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
                    conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
                    conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
                    break;

                case ActionMap.LIST_FILE:
                    conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
                    conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
                    conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        conf.put("savePath", savePath);
        conf.put("rootPath", uEditorConfig.getUploadPath());
        conf.put("urlPrefix", uEditorConfig.getUrlPrefix());
        return conf;

    }

    private void initEnv(String configPath) throws IOException {

        String configContent = this.readFile(configPath);

        try {
            JSONObject jsonConfig = new JSONObject(configContent);
            //统一url访问前缀
            if (uEditorConfig.getUnified()) {
                Iterator iterator = jsonConfig.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if (key.contains("UrlPrefix")) {
                        jsonConfig.put(key, uEditorConfig.getUrlPrefix());
                    }
                }
            }
            this.jsonConfig = jsonConfig;
        } catch (Exception e) {
            this.jsonConfig = null;
        }

    }

    private String[] getArray(String key) throws JSONException {

        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;

    }

    private String readFile(String path) throws IOException {

        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);

            String tmpContent = null;

            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }

            bfReader.close();

        } catch (UnsupportedEncodingException e) {
            // 忽略
        }

        return this.filter(builder.toString());

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
