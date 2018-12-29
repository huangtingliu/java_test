package com.huangtl.weixin.controller;

import com.huangtl.redis.RedisTest;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.bean.encryption.MiniUserInfoDecryptedData;
import com.huangtl.weixin.bean.encryption.MiniUserInfoEncryptedData;
import com.huangtl.weixin.bean.result.AjaxJson;
import com.huangtl.weixin.bean.result.AjaxJsonCode;
import com.huangtl.weixin.bean.result.Code2Session;
import com.huangtl.weixin.entity.HfWeixinUserInfoEntity;
import com.huangtl.weixin.enums.WxCodeEnum;
import com.huangtl.weixin.service.MsgService;
import com.huangtl.weixin.service.SystemService;
import com.huangtl.weixin.service.UserService;
import com.huangtl.weixin.utils.MiniUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 小程序业务控制
 * 开放平台绑定小程序：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1489144594_DhNoV&token=bd65016d817d9639807c4e1c73370b525697acb5&lang=zh_CN
 * 登录流程请查看：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
 */
@Controller
@RequestMapping("/mini")
public class MiniController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private MsgService msgService;
    @Resource(name = "wxUserService")
    private UserService userService;

    @RequestMapping()
    @ResponseBody
    public AjaxJson mini(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return AjaxJson.success("hello 小程序");
    }
    /**
     * 根据code换取 openId, sessionKey, unionId
     * https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html
     *
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/code2Session")
    @ResponseBody
    public AjaxJson code2Session(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String code = request.getParameter("code");

        Code2Session code2Session = userService.code2Session(code);
        if(!code2Session.isSuccess()){
            AjaxJson.fail(code2Session, WxCodeEnum.getMsgByCode(code2Session.getErrcode()));
        }

        Map result = new HashMap<>();
        result.put("code2Session",code2Session);
        String unionid = code2Session.getUnionid();

        //sessionkey存入缓存，用于其他接口签名加解密验证等
        userService.saveMiniSessionKey(code2Session);
        Set<String> keys = RedisTest.getJedis().keys("MINI");
        for (String key : keys) {
            System.out.println("key:'"+key+"';value='"+ RedisTest.getJedis().get(key)+"'");
        }

        if(StringUtil.isBlank(unionid)){
            System.out.println("获取unionid失败，未关注同主体的公众号用户请使用wx.getUserInfo从解密数据中获取 UnionID");
            return AjaxJson.fail(result,"获取unionid失败，未关注同主体的公众号用户请使用wx.getUserInfo从解密数据中获取 UnionID");
        }
        HfWeixinUserInfoEntity weixinUserInfo = new HfWeixinUserInfoEntity();
        //openid/unionid保存数据库
        List<HfWeixinUserInfoEntity> weixinInfos = systemService.findByProperty(HfWeixinUserInfoEntity.class, "unionid", unionid);
//        List<?> weixinInfos = null;// TODO: 2018/12/29 根据unionid查询数据库微信用户信息
        if(null!=weixinInfos && weixinInfos.size()>0){
            // TODO: 2018/12/29 这里可以返回根据unionid查询的一些信息给小程序 ，比如基本信息头像等
        }else{
            weixinUserInfo.setUnionid(unionid);
            weixinUserInfo.setCreateDate(new Date());
            systemService.save(weixinUserInfo);
            // TODO: 2018/12/29 这里保存基本信息到数据库
        }
        return AjaxJson.success(result);
    }

    /**
     * 根据小程序wx.getUserInfo方法返回值中的加密数据encryptedData获取详细的用户信息
     * https://developers.weixin.qq.com/miniprogram/dev/api/wx.getUserInfo.html
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/userInfoByEncryptedData")
    @ResponseBody
    public AjaxJson userInfoByEncryptedData(MiniUserInfoEncryptedData miniUserInfoEncryptedData, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String openid = request.getParameter("openid");
        String miniSessionKey = userService.getMiniSessionKey(openid);
        System.out.println(JsonUtils.objectToJson(miniUserInfoEncryptedData));
        System.out.println("miniSessionKey:"+miniSessionKey);
        if(StringUtil.isBlank(miniSessionKey)){
            return AjaxJson.fail("sessionKey为空", AjaxJsonCode.PARAM_NULL);
        }
        MiniUserInfoDecryptedData decryptedData = MiniUtils.decryptData(miniUserInfoEncryptedData, miniSessionKey);
        if(null==decryptedData){
            return AjaxJson.fail(decryptedData,"解密失败");
        }
        decryptedData.setWatermark(null);

        return AjaxJson.success(decryptedData);
    }



}
