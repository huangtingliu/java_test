package com.huangtl.weixin;

public class Constants {

    /**测试号appID*/
    public static final String appID="wx5d4227f15e0d1158";

    /**测试号appsecret*/
    public static final String appsecret="166529b45a47fbedb71995729b977cd4";

    /**接入微信服务器TOKEN验证token*/
    public static final String TOKEN="huangtl";

    /**redis存储access_token的key值*/
    public static final String ACCESS_TOKEN_KEY = "WX_ACCESS_TOKEN";

    public static final Integer CODE_SUCCESS = 0;


    /**
    * 获取access_token 接口调用请求
    * https请求方式: GET
    * 格式：https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
    * grant_type：必须，获取access_token填写client_credential
    * appid：必须，第三方用户唯一凭证
    * secret：必须，第三方用户唯一凭证密钥，即appsecret
    * */
    public static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 菜单创建接口
     * http请求方式：POST
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
     */
    public static final String URL_MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    /**
     * 获取关注用户列表
     * http请求方式: GET
     * next_openid：第一个拉取的OPENID，不填默认从头开始拉取
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840
     */
    public static final String URL_USER_INFO_LIST_GET = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";
    /**
     * 根据openid获取用户基本信息
     * http请求方式: GET
     */
    public static final String URL_USER_INFO_GET = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程
     * https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html
     * appid
     * secret
     * js_code
     */
    public static final String URL_CODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /**
     * 发送模板消息
     * http请求方式: POST
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
     */
    public static final String URL_TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    /**
     * 获取公众号的自动回复规则
     * http请求方式: GET
     */
    public static final String URL_GET_CURRENT_AUTOREPLY_INFO = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=%s";
}
