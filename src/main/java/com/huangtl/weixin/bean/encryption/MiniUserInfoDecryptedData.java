package com.huangtl.weixin.bean.encryption;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * 小程序wx.getUserInfo方法encryptedData参数 解密后结构，
 * https://developers.weixin.qq.com/miniprogram/dev/api/wx.getUserInfo.html
 {
     "openId": "OPENID",
     "nickName": "NICKNAME",
     "gender": GENDER,
     "city": "CITY",
     "province": "PROVINCE",
     "country": "COUNTRY",
     "avatarUrl": "AVATARURL",
     "unionId": "UNIONID",
     "watermark": {
         "appid": "APPID",
         "timestamp": TIMESTAMP
     }
 }
 */
public class MiniUserInfoDecryptedData {

    /**
     *
     */
    @JsonProperty(value = "openId")
    private String openId;
    /**
     *
     */
    @JsonProperty(value = "nickName")
    private String nickName;
    /**
     *
     */
    @JsonProperty(value = "gender")
    private String gender;
    /**
     *
     */
    @JsonProperty(value = "city")
    private String city;
    /**
     *
     */
    @JsonProperty(value = "province")
    private String province;
    /**
     *
     */
    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "language")
    private String language;

    @JsonProperty(value = "avatarUrl")
    private String avatarUrl;
    /**
     *
     */
    @JsonProperty(value = "unionId")
    private String unionId;
    /**
     *
     */
    @JsonProperty(value = "watermark")
    private Map watermark;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Map getWatermark() {
        return watermark;
    }

    public void setWatermark(Map watermark) {
        this.watermark = watermark;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
