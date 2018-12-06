package com.huangtl.weixin.bean.msg;

/**
 * 客服发消息格式
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547
 */
public class CustomerServicerMsg {

    /**
        是	普通用户openid
    */
    private String touser;

    /**
        是	消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，
            图文消息（点击跳转到外链）为news，图文消息（点击跳转到图文消息页面）为mpnews，卡券为wxcard，小程序为miniprogrampage
    */
    private String msgtype;

    /**
        是	文本消息内容
    */
    private String content;

    /**
        是	发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID
    */
    private String media_id;

    /**
        是	缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416
    */
    private String thumb_media_id;

    /**
        否	图文消息/视频消息/音乐消息/小程序卡片的标题
    */
    private String title;

    /**
        否	图文消息/视频消息/音乐消息的描述
    */
    private String description;

    /**
        是	音乐链接
    */
    private String musicurl;


    /**
        是	高品质音乐链接，wifi环境优先使用该链接播放音乐
    */
    private String hqmusicurl;

    /**
        否	图文消息被点击后跳转的链接
    */
    private String url;

    /**
        否	图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
    */
    private String picurl;

    /**
        是	小程序的appid，要求小程序的appid需要与公众号有关联关系
    */
    private String appid;

    /**
        是	小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
    */
    private String pagepath;

}
