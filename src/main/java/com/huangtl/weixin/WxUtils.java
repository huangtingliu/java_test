package com.huangtl.weixin;

import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.bean.result.AccessToken;
import com.huangtl.weixin.bean.Menu;
import com.huangtl.weixin.bean.result.WxResult;
import com.huangtl.weixin.enums.WxCodeEnum;
import com.huangtl.weixin.service.MsgService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

public class WxUtils {

    /**
     * 获取请求结果封装类
     * @param result
     * @return
     */
    public static WxResult getWxResult(String result) {
        WxResult wxResult = null;
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

        String sortStr = sort(Constants.TOKEN, timestamp, nonce);//排序串
        String sha1Str = DigestUtils.sha1Hex(sortStr);//sha1加密串

        System.out.println("加密后："+sha1Str);

        return sha1Str.equals(signature);

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

        String result = HttpUtils.get(String.format(Constants.URL_ACCESS_TOKEN, Constants.appID,Constants.appsecret));
        if(!StringUtils.isEmpty(result)){
            AccessToken token = JsonUtils.jsonToPojo(result, AccessToken.class);
            if(token.isSuccess()){
                RedisTest.getJedis().set(Constants.ACCESS_TOKEN_KEY,token.getAccess_token());
                return token.getAccess_token();
            }else{
                System.out.println(WxCodeEnum.getMsgByCode(token.getErrcode()));
            }
        }

        RedisTest.getJedis().del(Constants.ACCESS_TOKEN_KEY);

        return null;
    }

    /**
     * redis存在token则取redis的，否则重新微信获取
     * @return
     */
    public static String getAccessToken(){

        String redisAccessToken = RedisTest.getJedis().get(Constants.ACCESS_TOKEN_KEY);
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
        Menu button1 = new Menu("click","周六五黑","test","");
        Menu button2 = new Menu("view","英雄介绍","test","www.lol.com");
        Menu button3 = new Menu("","排行榜","","");

        //子菜单
        Menu child1 = new Menu("view","搜索","","http://www.soso.com/");
        Menu child2 = new Menu("click","排行榜","排行","");
        List<Menu> subButton = new ArrayList<>();
        subButton.add(child1);
        subButton.add(child2);
        button3.setSub_button(subButton);

        List<Menu> button = new ArrayList<>();
        button.add(button1);
//        button.add(button2);
        button.add(button3);
        menu.setButton(button);

        try {
            String menuJson = JsonUtils.objectToJson(menu);
            String result = HttpUtils.post(String.format(Constants.URL_MENU_CREATE, getAccessToken()), menuJson);
            WxResult wxResult = getWxResult(result);
            if(!wxResult.isSuccess()){
                System.out.println(WxCodeEnum.getMsgByCode(wxResult.getErrcode()));
                if(isAccessTokenErr(wxResult)){
                    return createMenu();
                }else {
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        return true;

    }

    public static void main(String[] args) {

        String testOpenid = "om9ct1GJoXoxg0xZ4iUHlv1xOo6c";

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
//        MsgService.sendTemplateMsg("om9ct1GJoXoxg0xZ4iUHlv1xOo6c");
        MsgService.sendTemplateMsg2("om9ct1EGNPKFpHZ5Ecjptc1nIG6c");


        /**获取自动回复规则*/
//        String autoReplyInfo = HttpUtils.get(String.format(Constants.URL_GET_CURRENT_AUTOREPLY_INFO, getAccessToken()));
//        System.out.println(autoReplyInfo);
    }

}
