package com.huangtl.weixin.service;

import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.WxUserInfo;
import com.huangtl.weixin.bean.result.Code2Session;
import com.huangtl.weixin.utils.MPUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 公众号/小程序 登录业务
 *
 * 小程序：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
 */
@Service("wxUserService")
public class UserService {


    /**
     * 根据code换取 openId, sessionKey, unionId
     * https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html
     *
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     */
    public Code2Session code2Session(String code){
        if(StringUtils.isEmpty(code)){
            return null;
        }
        String result = HttpUtils.get(String.format(Constants.URL_CODE2SESSION, Constants.MINI_APPID,Constants.MINI_APPSECRET,code));

        Code2Session code2Session = JsonUtils.jsonToPojo(result, Code2Session.class);

        return code2Session;
    }

    /**
     * 公众号openid保存数据库
     * @param openid
     */
    public void saveOpenid(String openid){
        try{

            System.out.println("公众号openid("+openid+")保存数据库");
            WxUserInfo weixinUserInfo = null;//TODO: 这里要改为从数据库查询微信用户信息
            if(null==weixinUserInfo){
                System.out.println("没有查找到 openid 记录，更新 ");
                //获取unionid,并查找是否有相关用户，有则更新没有保存
                WxUserInfo wxUser = getWxUserInfoByOpenId(openid);
                if(null!=wxUser){
//                    weixinUserInfo = systemService.findUniqueByProperty(HfWeixinUserInfoEntity.class,"unionid",wxUser.getUnionid());
                    weixinUserInfo = null;// TODO: 2018/12/29 修改为根据union从数据库查询唯一用户
                    if(null!=weixinUserInfo){

                        System.out.println("更新用户openid");
                        weixinUserInfo.setOpenid(wxUser.getOpenid());
//                        systemService.updateEntitie(weixinUserInfo);
                        // TODO: 2018/12/29 更新用户openid
                    }else if(!StringUtil.isBlank(wxUser.getUnionid())){
                        //保存新用户
                        System.out.println("保存新用户");
                        // TODO: 2018/12/29 这里保存新的用户数据到数据库,用户基本信息
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据openid从第三方获取微信用户信息
     * 注意：公众号的openid才能获取
     * @param openid
     * @return
     */
    public WxUserInfo getWxUserInfoByOpenId(String openid){
        String userInfoResult = MPUtils.getCheckToken(Constants.URL_USER_INFO_GET, MPUtils.getAccessToken(), openid);
        WxUserInfo wxUserInfo = JsonUtils.jsonToPojo(userInfoResult, WxUserInfo.class);

        return wxUserInfo;
    }

    /**
     * 根据openid获取当前用户
     * @param unionid
     * @return
     */
    public WxUserInfo getSessionUser(String unionid){
        WxUserInfo wxUserInfo = new WxUserInfo();
//        String userInfoJson = JedisUtils.get(Constants.SESSION_USER_KEY_PREFIX + unionid);
        String userInfoJson = RedisTest.getJedis().get(Constants.SESSION_USER_KEY_PREFIX + unionid);
        if(!StringUtils.isEmpty(userInfoJson)){
            wxUserInfo = JsonUtils.jsonToPojo(userInfoJson, WxUserInfo.class);
        }else{
            //从数据库获取
        }

        return wxUserInfo;
    }

    /**
     * 保存小程序sessionkey到缓存
     * @param code2Session
     */
    public void saveMiniSessionKey(Code2Session code2Session){
        String redisSessionKey = Constants.MINI_SESSION_KEY_PREFIX+code2Session.getOpenid();
        String redisSessionValue = code2Session.getSession_key();
        RedisTest.getJedis().set(redisSessionKey,redisSessionValue);
    }

    /**
     * 获取缓存小程序sessionkey
     */
    public String getMiniSessionKey(String openid){
        return RedisTest.getJedis().get(Constants.MINI_SESSION_KEY_PREFIX+openid);
    }

}
