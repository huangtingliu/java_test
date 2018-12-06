package com.huangtl.weixin.bean.msg;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 消息公共参数（普通消息、事件推送消息、被动回复用户消息）
 */
public class BaseMsg  implements Serializable {

    @JsonProperty(value = "ToUserName")
    /**开发者 微信号*/
    private String ToUserName;

    @JsonProperty(value = "FromUserName")
    /**发送方帐号（一个OpenID）*/
    private String FromUserName;

    @JsonProperty(value = "CreateTime")
    /**消息创建时间 （整型）*/
    private Long CreateTime;

    @JsonProperty(value = "MsgType")
    /**消息类型*/
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
