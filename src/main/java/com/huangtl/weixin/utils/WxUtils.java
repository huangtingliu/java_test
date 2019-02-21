package com.huangtl.weixin.utils;


import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.result.AccessToken;
import com.huangtl.weixin.bean.result.WxResult;
import com.huangtl.weixin.enums.WxCodeEnum;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class WxUtils {

    private String accessTokenKey;
    private String appid;
    private String appsecret;

    public WxUtils(String accessTokenKey, String appid, String appsecret) {
        this.accessTokenKey = accessTokenKey;
        this.appid = appid;
        this.appsecret = appsecret;
    }


    /**
     * 获取请求结果封装类
     * @param result
     * @return
     */
    public static WxResult getWxResult(String result) {
        WxResult wxResult = new WxResult();
        try {
            wxResult = JsonUtils.jsonToPojo(result, WxResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxResult;
    }
    /**
     * 获取请求结果封装类
     * @param result
     * @return
     */
    public static <T> T getWxResult(String result,Class<T> clazz) {
        T t = null;
        try {
            t = JsonUtils.jsonToPojo(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 字典序排序
     * @return
     */
    public static String sort(String token,String timestamp,String nonce){
        String[] arr = { token, timestamp, nonce };
        Arrays.sort(arr); // 字典序排序
        String str = arr[0] + arr[1] + arr[2];
        return str;
    }

    /**
     * 获取accessToken和参数的拼装url
     * @return
     */
    public static String getTokenParamUrl(String url,String accessToken,String ...urlParam){
        url = url.replaceFirst("%s",accessToken);
        for(int i =0;i<urlParam.length;i++){
            url = url.replaceFirst("%s",urlParam[i]);
        }
        return url;
    }

    /**
     * 检查token的POST请求，如果是token过期失败会重新获取token并使用新的token重新提交
     * @param url 请求路径 accessToken参数一点要在第一个参数
     * @param param 业务请求参数，不包括accessToken
     * @param accessToken accessToken
     * @param urlParam 请求路径中携带的参数
     * @return
     */
    public String postCheckToken(String url,String param,String accessToken,String ...urlParam){

        String result = HttpUtils.post(getTokenParamUrl(url,accessToken,urlParam), param);
        WxResult wxResult = WxUtils.getWxResult(result);
        if(!wxResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(wxResult.getErrcode()));
            if(isAccessTokenErr(wxResult)){
                result = HttpUtils.post(getTokenParamUrl(url, getAccessToken(),urlParam), param);
            }else {

            }
        }
        return result;
    }

    /**
     *
     * 检查token的GET请求，如果是token过期失败会重新获取token并使用新的token重新提交
     * @param url 请求路径 accessToken参数一点要在第一个参数
     * @param accessToken accessToken
     * @param urlParam 请求路径中携带的参数
     * @+
     */
    public String getCheckToken(String url,String accessToken,String ...urlParam){

        String result = HttpUtils.get(getTokenParamUrl(url,accessToken,urlParam));
        WxResult wxResult = WxUtils.getWxResult(result);
        if(!wxResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(wxResult.getErrcode()));
            if(isAccessTokenErr(wxResult)){
                result = HttpUtils.get(getTokenParamUrl(url, getAccessToken(),urlParam));
            }else {

            }
        }
        return result;
    }

    /**
     * 检查是否是AccessToken超时错误，如果是则重新获取
     * @param wxResult
     * @return
     */
    public boolean isAccessTokenErr(WxResult wxResult){
        if(null!=wxResult && wxResult.getErrcode().equals(WxCodeEnum.code59.getCode())){
            System.out.println("AccessToken超时错误");
            getNewAccessToken();
            return true;
        }

        return false;
    }

    /**
     * 获取access_token
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
     * 返回：{"access_token":"ACCESS_TOKEN","expires_in":7200}
     *
     * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     *
     * @return access_token
     */
    public String getNewAccessToken(){

        String result = HttpUtils.get(String.format(Constants.URL_ACCESS_TOKEN, appid,appsecret));
        if(!StringUtils.isEmpty(result)){
            AccessToken token = JsonUtils.jsonToPojo(result, AccessToken.class);
            if(token.isSuccess()){
                RedisTest.getJedis().set(accessTokenKey,token.getAccess_token());
                return token.getAccess_token();
            }else{
                System.out.println(WxCodeEnum.getMsgByCode(token.getErrcode()));
            }
        }

        RedisTest.getJedis().del(accessTokenKey);

        return null;
    }

    /**
     * redis存在token则取redis的，否则重新微信获取
     * @return
     */
    public String getAccessToken(){

        String redisAccessToken = RedisTest.getJedis().get(accessTokenKey);
        if(!StringUtils.isEmpty(redisAccessToken)){
            return redisAccessToken;
        }else{
            return getNewAccessToken();
        }

    }

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
