package com.huangtl.weixin.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 小程序
 */
public class Miniprogram {

    /**所需跳转到的小程序appid*/
    @JsonProperty(value = "appid")
    private String appid;

    @JsonProperty(value = "pagepath")
    private String pagepath;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
