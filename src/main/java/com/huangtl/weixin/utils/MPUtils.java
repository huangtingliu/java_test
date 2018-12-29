package com.huangtl.weixin.utils;


import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.Menu;
import com.huangtl.weixin.bean.result.AccessToken;
import com.huangtl.weixin.bean.result.WxResult;
import com.huangtl.weixin.enums.WxCodeEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 公众号工具类
 */
public class MPUtils {


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
    public static String getCheckToken(String url,String accessToken,String... urlParam){
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
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319
        开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：
        参数	描述
        signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        timestamp	时间戳
        nonce	随机数
        echostr	随机字符串
        开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。
        加密/校验流程如下：
            1）将token、timestamp、nonce三个参数进行字典序排序
            2）将三个参数字符串拼接成一个字符串进行sha1加密
            3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
    */
    public static boolean checkToken(String signature,String timestamp,String nonce,String echostr){

        String sortStr = WxUtils.sort(Constants.TOKEN, timestamp, nonce);//排序串
        String sha1Str = DigestUtils.sha1Hex(sortStr);//sha1加密串

        System.out.println("加密后："+sha1Str);

        return sha1Str.equals(signature);

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

        String result = HttpUtils.get(String.format(Constants.URL_ACCESS_TOKEN, Constants.MP_APPID,Constants.MP_APPSECRET));
        if(!StringUtils.isEmpty(result)){
            AccessToken token = JsonUtils.jsonToPojo(result, AccessToken.class);
            if(token.isSuccess()){
                RedisTest.getJedis().set(Constants.MP_ACCESS_TOKEN_KEY,token.getAccess_token());
                return token.getAccess_token();
            }else{
                System.out.println(WxCodeEnum.getMsgByCode(token.getErrcode()));
            }
        }

        RedisTest.getJedis().del(Constants.MP_ACCESS_TOKEN_KEY);

        return null;
    }

    /**
     * redis存在token则取redis的，否则重新微信获取
     * @return
     */
    public static String getAccessToken(){

        String redisAccessToken = RedisTest.getJedis().get(Constants.MP_ACCESS_TOKEN_KEY);
        if(!StringUtils.isEmpty(redisAccessToken)){
            return redisAccessToken;
        }else{
            return getNewAccessToken();
        }

    }

    /**
     * 创建公众号菜单
     * @return
     */
    public static boolean createMenu(){

        //最终请求的菜单格式
        Menu menu = new Menu();

        //主菜单，最多三个
        Menu button1 = new Menu("click","生活服务","live","");
        Menu button2 = new Menu("","加盟商","","");
        Menu button3 = new Menu("click","关于我们","aboult","");

        //子菜单
        Menu child1 = new Menu("miniprogram","工单管理","order",Constants.MINI_PATH);
        child1.setAppid(Constants.MINI_APPID);
        child1.setPagepath("pages/login/login");
        Menu child2 = new Menu("click","申请加盟","approval","");
        List<Menu> subButton = new ArrayList<>();
        subButton.add(child1);
        subButton.add(child2);
        button2.setSub_button(subButton);

        List<Menu> button = new ArrayList<>();
        button.add(button1);
        button.add(button2);
        button.add(button3);
        menu.setButton(button);

        try {
            String menuJson = JsonUtils.objectToJson(menu);

            System.out.println(menuJson);
            String result = postCheckToken(Constants.URL_MENU_CREATE,menuJson, getAccessToken());
            WxResult wxResult = WxUtils.getWxResult(result);

            return wxResult.isSuccess();
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

    }

    public static void main(String[] args) {

//        String acctoken = "16_y4K_lgg6HkN6WB1Q2YN7sM-Jb1EaC1J5ZMTVJr9IjbEAeF2LsFvjMHZ5YMpYJvdCEljtjM4TI4IJim8rG-hCB7A-jMmz0Nc9-rcrNO9I9j3u9FbFEprxrszvnfYULOfAGAGXA";
//        String userInfoResult = MPUtils.getCheckToken(Constants.URL_USER_INFO_GET, acctoken, "oo-LgwcCXCz_tMTo1qZg_pszUWZE".toString());
//        System.out.println(userInfoResult);


/**获取随机数*/
//        for (int i=0;i<=31;i++){
//            String v = String.valueOf ((int)((Math.random() * 9 +1) * 100000));
//            System.out.println(v);
//        }
//        String testOpenid = "om9ct1GJoXoxg0xZ4iUHlv1xOo6c";

        /**获取并保存新的accessToken*/
//        getNewAccessToken();

        /**创建菜单*/
//        createMenu();

        /**获取用户列表*/
//        String userList = HttpUtils.get(String.format(Constants.URL_USER_INFO_LIST_GET, getAccessToken(), ""));
//        System.out.println(userList);

        /**获取用户信息*/
//        String userInfo = HttpUtils.get(String.format(Constants.URL_USER_INFO_GET, getAccessToken(), testOpenid));
//        System.out.println(userInfo);

        /**发送模板消息*/
//        JedisUtils.del(Constants.ACCESS_TOKEN_KEY);
//        MsgService.sendTemplateMsg(testOpenid);
//        MsgService.sendTemplateMsg2(testOpenid);


        /**获取自动回复规则*/
//        String autoReplyInfo = HttpUtils.get(String.format(Constants.URL_GET_CURRENT_AUTOREPLY_INFO, getAccessToken()));
//        System.out.println(autoReplyInfo);
    }

}
