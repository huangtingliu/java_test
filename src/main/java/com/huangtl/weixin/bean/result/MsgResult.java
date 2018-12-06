package com.huangtl.weixin.bean.result;

import org.codehaus.jackson.annotate.JsonProperty;

public class MsgResult extends WxResult {

    @JsonProperty(value = "msgid")
    private String msgid;

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
