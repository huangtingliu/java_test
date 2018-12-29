package com.huangtl.weixin.service;

import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.bean.Miniprogram;
import com.huangtl.weixin.bean.event.MenuEvent;
import com.huangtl.weixin.bean.event.SubscribeEvent;
import com.huangtl.weixin.bean.event.TemplateSendEvent;
import com.huangtl.weixin.bean.msg.SendMsg;
import com.huangtl.weixin.bean.msg.SendTemplateMsg;
import com.huangtl.weixin.bean.msg.TemplateMsgNewOrder;
import com.huangtl.weixin.enums.EventType;
import com.huangtl.weixin.utils.MPUtils;
import com.huangtl.weixin.utils.MessgaeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息服务处理
 */
@Service
public class MsgService {

//    private static final Logger logger = Logger.getLogger(MsgService.class);

    @Resource(name = "wxUserService")
    private UserService userService;
//    @Autowired
//    private SystemService systemService;
    /**
     * 处理事件类型消息
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
     * @param map
     * @param request
     * @param response
     */
    public void event(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response){

        try {

            EventType eventType = EventType.valueOf(MessgaeUtils.getEventType(map).toUpperCase());
            SendMsg sendMsg = new SendMsg();
            String sendMsgXml = "";
            switch (eventType){
                case SUBSCRIBE:
                    //订阅

                    System.out.println("用户关注");
                    SubscribeEvent subscribeEvent = JsonUtils.mapToBean(map, SubscribeEvent.class);

                    //保存信息
                    String fromUserName = subscribeEvent.getFromUserName();//用户openid
//                    logger.debug("openid fromUserName:"+fromUserName);
                    userService.saveOpenid(fromUserName);

    //                sendMsg.setMsgId("11111111111");
                    sendMsg.setContent("欢迎来到xx,感谢您的关注。");
                    sendMsg.setMsgType("text");
                    sendMsg.setFromUserName(subscribeEvent.getToUserName());
                    sendMsg.setToUserName(subscribeEvent.getFromUserName());

                    sendMsgXml = MessgaeUtils.objectToXml(sendMsg);

                    write(response,sendMsgXml);
//                    write(response,"success");

                    break;
                case UNSUBSCRIBE:
                    //取消关注
                    System.out.println("用户取消关注");
                    break;
                case SCAN:
                    //扫描二维码
                    System.out.println("用户扫描二维码");
                    break;
                case LOCATION:
                    //上报地理位置
                    System.out.println("用户上报了地址");
                    break;
                case CLICK:
                    //菜单单击
                    MenuEvent menuEvent = JsonUtils.mapToBean(map,MenuEvent.class);

    //                sendMsg.setMsgId("11111111111");
//                    sendMsg.setContent("你点击了菜单："+menuEvent.getEventKey());
                    sendMsg.setContent("敬请期待");
                    sendMsg.setMsgType("text");
                    sendMsg.setFromUserName(menuEvent.getToUserName());
                    sendMsg.setToUserName(menuEvent.getFromUserName());

                    sendMsgXml = MessgaeUtils.objectToXml(sendMsg);

                    write(response,sendMsgXml);

//                    write(response,"success");
                    break;
                case VIEW:
                    //菜单跳转
                    System.out.println("用户点击菜单发生跳转");
                    break;
                case VIEW_MINIPROGRAM:
                    //小程序跳转
                    System.out.println("用户点击菜单发生小程序跳转");
                    break;
                case TEMPLATESENDJOBFINISH:
                    //发送模板消息结果返回
                    System.out.println("发送模板消息返回结果");
                    TemplateSendEvent event = JsonUtils.mapToBean(map,TemplateSendEvent.class);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void text(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
//        SendMsg sendMsg = new SendMsg();
//        String sendMsgXml = "";
//
//        SendMsg receiveMsg = JsonUtils.mapToBean(map,SendMsg.class);
//        sendMsg.setMsgId(receiveMsg.getMsgId());
//        sendMsg.setContent("你发送了消息："+receiveMsg.getContent());
//        sendMsg.setMsgType("text");
//        sendMsg.setFromUserName(receiveMsg.getToUserName());
//        sendMsg.setToUserName(receiveMsg.getFromUserName());
//
//        sendMsgXml = MessgaeUtils.objectToXml(sendMsg);
//
//        write(response,sendMsgXml);
        write(response,"success");
    }

    /**
     * 发送新订单模板消息
     * @param openid
     * @param msg
     */
    public static void sendTemplateMsgNewOrder(String openid,TemplateMsgNewOrder newOrder){
        SendTemplateMsg msg = new SendTemplateMsg();
        msg.setTouser(openid);
        Map<String, SendTemplateMsg.DataItem> data = new HashMap<>();
        data.put("first",new SendTemplateMsg.DataItem("居民"+newOrder.getUserName()+"预约服务"));
        data.put("keyword1",new SendTemplateMsg.DataItem(newOrder.getServiceName()));
        data.put("keyword2",new SendTemplateMsg.DataItem(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(newOrder.getServiceTime())));
        data.put("keyword3",new SendTemplateMsg.DataItem(newOrder.getUserName()));
        data.put("keyword4",new SendTemplateMsg.DataItem(newOrder.getPhone()));
        data.put("keyword5",new SendTemplateMsg.DataItem(newOrder.getAddress()));
        data.put("remark",new SendTemplateMsg.DataItem("请您尽快处理"));

        Miniprogram miniprogram = new Miniprogram();
        miniprogram.setAppid(Constants.MINI_APPID);
        miniprogram.setPath(String.format(Constants.MINI_PATH,newOrder.getOrderId()));

        msg.setMiniprogram(miniprogram);
        msg.setData(data);
        msg.setTemplate_id(Constants.TEMPLATE_ID_NEW_ORDER);
        sendTemplateMsg(openid,msg);
    }

    public static void sendTemplateMsg(String openid,SendTemplateMsg msg){

        String msgJson = "";
        try {
            msgJson = JsonUtils.objectToJson(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msgJson);

        String result = MPUtils.postCheckToken(Constants.URL_TEMPLATE_SEND,msgJson, MPUtils.getAccessToken());
        System.out.println(result);
    }

    private void write(HttpServletResponse response, String xml){
        try {
            response.getWriter().print(xml);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
