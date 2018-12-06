package com.huangtl.weixin.bean.event;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 模板消息发送事件推送
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
 */
public class TemplateSendEvent extends BaseEvent {

    @JsonProperty(value = "MsgID")
    /**消息id*/
    private String MsgID;

    @JsonProperty(value = "Status")
    private String Status;//送达成功:success;用户拒收:failed:user block；其他原因失败时：failed: system failed

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
