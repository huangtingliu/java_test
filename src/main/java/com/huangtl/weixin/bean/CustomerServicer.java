package com.huangtl.weixin.bean;

/**
 * 客服
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547
 */
public class CustomerServicer {

    /**完整客服账号，格式为：账号前缀@公众号微信号*/
    private String kf_account;

    /**客服昵称*/
    private String kf_nick;

    /**客服工号*/
    private String kf_id;

    /**客服昵称，最长6个汉字或12个英文字符*/
    private String nickname;

    /**
     * 非必须
     * 客服账号登录密码，格式为密码明文的32位加密MD5值。
     * 该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
     */
    private String password;

    /**该参数仅在设置客服头像时出现，是form-data中媒体文件标识，有filename、filelength、content-type等信息*/
    private String media;

    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    public String getKf_nick() {
        return kf_nick;
    }

    public void setKf_nick(String kf_nick) {
        this.kf_nick = kf_nick;
    }

    public String getKf_id() {
        return kf_id;
    }

    public void setKf_id(String kf_id) {
        this.kf_id = kf_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
