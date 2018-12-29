package com.huangtl.weixin.bean.result;

import com.huangtl.weixin.Constants;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WxResult {

    @JsonProperty(value = "errcode")
    private Integer errcode;//错误码

    @JsonProperty(value = "errmsg")
    private String errmsg;//错误信息

    public boolean isSuccess(){
        return null == errcode || errcode.equals(Constants.CODE_SUCCESS);
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
