package com.huangtl.weixin.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 小程序
 */
public class Miniprogram {

    /**所需跳转到的小程序appid*/
    @JsonProperty(value = "appid")
    private String appid;

//    @JsonProperty(value = "pagepath")
//    private String pagepath;
    @JsonProperty(value = "path")
    private String path;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
