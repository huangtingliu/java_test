package com.huangtl.weixin.bean.encryption;

/**
 * 小程序wx.getUserInfo方法返回参数，
 * https://developers.weixin.qq.com/miniprogram/dev/api/wx.getUserInfo.html
 */
public class MiniUserInfoEncryptedData {

    /**
     * 不包括敏感信息的原始数据字符串，用于计算签名
     */
    private String rawData;

    /**
     * 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html
     */
    private String signature;

    /**
     * 包括敏感数据在内的完整用户信息的加密数据，详见 用户数据的签名验证和加解密
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html
     */
    private String encryptedData;

    /**
     * 加密算法的初始向量，详见 用户数据的签名验证和加解密
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html
     */
    private String iv;


    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
