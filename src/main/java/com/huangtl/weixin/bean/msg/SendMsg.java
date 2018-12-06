package com.huangtl.weixin.bean.msg;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * 发送/返回给微信的消息对象
 */
public class SendMsg extends BaseMsg {

    @JsonProperty(value = "Content")
    private String Content;
    @JsonProperty(value = "MsgId")
    private String MsgId;

    public SendMsg() {
        this.setCreateTime(new Date().getTime());
    }

    public SendMsg(String MsgType,String FromUserName,String ToUserName, String msgId,String content) {
        Content = content;
        MsgId = msgId;
        this.setCreateTime(new Date().getTime());
        this.setFromUserName(FromUserName);
        this.setToUserName(ToUserName);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
