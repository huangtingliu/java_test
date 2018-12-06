package com.huangtl.weixin.bean.event;

import com.huangtl.weixin.bean.msg.BaseMsg;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 事件公共类
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
 */
public class BaseEvent extends BaseMsg implements Serializable{


    @JsonProperty(value = "Event")
    /**事件类型，CLICK/VIEW*/
    private String Event;

    /**事件KEY值*/
    @JsonProperty(value = "EventKey")
    private String EventKey;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
