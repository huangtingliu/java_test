package com.huangtl.weixin.utils;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.encryption.MiniUserInfoDecryptedData;
import com.huangtl.weixin.bean.encryption.MiniUserInfoEncryptedData;
import com.huangtl.weixin.bean.result.AccessToken;
import com.huangtl.weixin.bean.result.WxResult;
import com.huangtl.weixin.enums.WxCodeEnum;
import org.springframework.util.StringUtils;

import java.util.Base64;

/**
 * 小程序工具类
 */
public class MiniUtils {


    /**
     * 检查token的POST请求，如果是token过期失败会重新获取token并使用新的token重新提交
     * @param url 请求路径 accessToken参数一点要在第一个参数
     * @param param 业务请求参数，不包括accessToken
     * @param accessToken accessToken
     * @param urlParam 请求路径中携带的参数
     * @return
     */
    public static String postCheckToken(String url,String param,String accessToken,String ...urlParam){
        url = url.replaceFirst("%s",accessToken);
        for(int i =0;i<urlParam.length;i++){
            url = url.replaceFirst("%s",urlParam[i]);
        }
        String result = HttpUtils.post(url, param);
        WxResult wxResult = WxUtils.getWxResult(result);
        if(!wxResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(wxResult.getErrcode()));
            if(isAccessTokenErr(wxResult)){
                result = HttpUtils.post(String.format(url, getAccessToken(),urlParam), param);
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
    public static String getCheckToken(String url,String accessToken,String ...urlParam){
        url = url.replaceFirst("%s",accessToken);
        for(int i =0;i<urlParam.length;i++){
            url = url.replaceFirst("%s",urlParam[i]);
        }
        String result = HttpUtils.get(url);
        WxResult wxResult = WxUtils.getWxResult(result);
        if(!wxResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(wxResult.getErrcode()));
            if(isAccessTokenErr(wxResult)){
                result = HttpUtils.get(String.format(url, getAccessToken(),urlParam));
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
    public static boolean isAccessTokenErr(WxResult wxResult){
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
    public static String getNewAccessToken(){

        String result = HttpUtils.get(String.format(Constants.URL_ACCESS_TOKEN, Constants.MINI_APPID,Constants.MINI_APPSECRET));
        if(!StringUtils.isEmpty(result)){
            AccessToken token = JsonUtils.jsonToPojo(result, AccessToken.class);
            if(token.isSuccess()){
                RedisTest.getJedis().set(Constants.MINI_ACCESS_TOKEN_KEY,token.getAccess_token());
                return token.getAccess_token();
            }else{
                System.out.println(WxCodeEnum.getMsgByCode(token.getErrcode()));
            }
        }

        RedisTest.getJedis().del(Constants.MINI_ACCESS_TOKEN_KEY);

        return null;
    }

    /**
     * redis存在token则取redis的，否则重新微信获取
     * @return
     */
    public static String getAccessToken(){

        String redisAccessToken = RedisTest.getJedis().get(Constants.MINI_ACCESS_TOKEN_KEY);
        if(!StringUtils.isEmpty(redisAccessToken)){
            return redisAccessToken;
        }else{
            return getNewAccessToken();
        }

    }


    /**
         加密数据解密算法
         接口如果涉及敏感数据（如wx.getUserInfo当中的 openId 和 unionId），接口的明文内容将不包含这些敏感数据。
         开发者如需要获取敏感数据，需要对接口返回的加密数据(encryptedData) 进行对称解密。 解密算法如下：
         1.对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
         2.对称解密的目标密文为 Base64_Decode(encryptedData)。
         3.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
         4.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
     */
    public static MiniUserInfoDecryptedData decryptData(MiniUserInfoEncryptedData encryptedData, String sessionKey){
        MiniUserInfoDecryptedData decryptedData = new MiniUserInfoDecryptedData();
//        byte[] dataBytes = Base64.decode(encryptedData.getEncryptedData());
//        byte[] ivBytes = Base64.decode(encryptedData.getIv());
//        byte[] sessionkeyBytes = Base64.decode(sessionKey);

        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] result = AES.decrypt(decoder.decode(encryptedData.getEncryptedData()), decoder.decode(sessionKey),
                    AES.generateIV(decoder.decode(encryptedData.getIv())));
//            String s=StringUtils.toString(result,"UTF-8");
            String json = new String(result,"UTF-8");
            decryptedData = JsonUtils.jsonToPojo(json,MiniUserInfoDecryptedData.class);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return decryptedData;
    }


    public static void main(String[] args) {

        String testOpenid = "";

        /**获取并保存新的accessToken*/
//        getNewAccessToken();

    }

}
