package com.huangtl.weixin.bean.msg;

import com.huangtl.weixin.bean.Miniprogram;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * 发送模板消息格式
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
 *  {
         "touser":"OPENID",
         "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
         "url":"http://weixin.qq.com/download",
         "miniprogram":{
             "appid":"xiaochengxuappid12345",
             "pagepath":"index?foo=bar"
         },
         "data":{
             "first": {
                 "value":"恭喜你购买成功！",
                 "color":"#173177"
             },
             "keyword1":{
                 "value":"巧克力",
                 "color":"#173177"
             },
             "keyword2": {
                 "value":"39.8元",
                 "color":"#173177"
             },
             "keyword3": {
                 "value":"2014年9月22日",
                 "color":"#173177"
             },
             "remark":{
                 "value":"欢迎再次购买！",
                 "color":"#173177"
             }
         }
     }
 */
public class SendTemplateMsg{

    /**（必填）接收者openid*/
    @JsonProperty(value = "touser")
    private String touser;

    /**（必填）模板ID*/
    @JsonProperty(value = "template_id")
    private String template_id;

    /**模板跳转链接*/
    @JsonProperty(value = "url")
    private String url;

    /**跳转的小程序*/
    @JsonProperty(value = "miniprogram")
    private Miniprogram miniprogram;

    /**（必填）模板数据*/
    @JsonProperty(value = "data")
    private Map<String,DataItem> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Miniprogram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Miniprogram miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, DataItem> getData() {
        return data;
    }

    public void setData(Map<String, DataItem> data) {
        this.data = data;
    }

    /**
     * 模板数据项
     */
    public static class DataItem
    {
        /**数据值*/
        private String value;

        /**模板内容字体颜色，不填默认为黑色*/
        private String color;

        public DataItem() {
        }

        public DataItem(String value) {
            this.value = value;
        }

        public DataItem(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
