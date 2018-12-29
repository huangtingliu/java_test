package com.huangtl.weixin.bean.result;

/**
 * 小程序根据code获取登录凭证结果
 * https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html
 */
public class Code2Session extends WxResult{

    private String openid; //用户唯一标识
    private String session_key;//会话密钥
    private String unionid;//用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
