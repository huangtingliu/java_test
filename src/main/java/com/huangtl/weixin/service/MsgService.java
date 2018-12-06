package com.huangtl.weixin.service;

import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.Constants;
import com.huangtl.weixin.WxUtils;
import com.huangtl.weixin.bean.event.MenuEvent;
import com.huangtl.weixin.bean.event.SubscribeEvent;
import com.huangtl.weixin.bean.event.TemplateSendEvent;
import com.huangtl.weixin.bean.msg.SendMsg;
import com.huangtl.weixin.bean.msg.SendTemplateMsg;
import com.huangtl.weixin.bean.result.MsgResult;
import com.huangtl.weixin.enums.EventType;
import com.huangtl.weixin.enums.WxCodeEnum;
import com.huangtl.weixin.utils.MessgaeUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息服务处理
 */
@Service
public class MsgService {

    /**
     * 处理事件类型消息
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
     * @param map
     * @param request
     * @param response
     */
    public void event(Map<String, Object> map,HttpServletRequest request, HttpServletResponse response){

        EventType eventType = EventType.valueOf(MessgaeUtils.getEventType(map).toUpperCase());
        SendMsg sendMsg = new SendMsg();
        String sendMsgXml = "";
        switch (eventType){
            case SUBSCRIBE:
                //订阅
                System.out.println("用户关注");
                SubscribeEvent subscribeEvent = JsonUtils.mapToBean(map, SubscribeEvent.class);
//                sendMsg.setMsgId("11111111111");
                sendMsg.setContent("欢迎来到xxx,感谢您的关注。");
                sendMsg.setMsgType("text");
                sendMsg.setFromUserName(subscribeEvent.getToUserName());
                sendMsg.setToUserName(subscribeEvent.getFromUserName());

                sendMsgXml = MessgaeUtils.objectToXml(sendMsg);

                write(response,sendMsgXml);
                break;
            case UNSUBSCRIBE:
                //取消关注
                System.out.println("用户取消关注");
                break;
            case SCAN:
                //扫墓二维码
                System.out.println("用户扫墓二维码");
                break;
            case LOCATION:
                //上报地理位置
                System.out.println("用户上报了地址");
                break;
            case CLICK:
                //菜单单击
                MenuEvent menuEvent = JsonUtils.mapToBean(map,MenuEvent.class);

//                sendMsg.setMsgId("11111111111");
                sendMsg.setContent("你点击了菜单："+menuEvent.getEventKey());
                sendMsg.setMsgType("text");
                sendMsg.setFromUserName(menuEvent.getToUserName());
                sendMsg.setToUserName(menuEvent.getFromUserName());

                sendMsgXml = MessgaeUtils.objectToXml(sendMsg);

                write(response,sendMsgXml);

                break;
            case VIEW:
                //菜单跳转
                System.out.println("用户点击菜单发生跳转");
                break;
            case TEMPLATESENDJOBFINISH:
                //发送模板消息结果返回
                System.out.println("发送模板消息返回结果");
                TemplateSendEvent event = JsonUtils.mapToBean(map,TemplateSendEvent.class);
                break;
        }
    }

    public void text(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        SendMsg sendMsg = new SendMsg();
        String sendMsgXml = "";

        SendMsg receiveMsg = JsonUtils.mapToBean(map,SendMsg.class);
        sendMsg.setMsgId(receiveMsg.getMsgId());
        sendMsg.setContent("你发送了消息："+receiveMsg.getContent());
        sendMsg.setMsgType("text");
        sendMsg.setFromUserName(receiveMsg.getToUserName());
        sendMsg.setToUserName(receiveMsg.getFromUserName());

        sendMsgXml = MessgaeUtils.objectToXml(sendMsg);

        write(response,sendMsgXml);
    }

    public static void sendTemplateMsg(String openid){
//        om9ct1GJoXoxg0xZ4iUHlv1xOo6c
        SendTemplateMsg msg = new SendTemplateMsg();
        msg.setTouser(openid);
        Map<String, SendTemplateMsg.DataItem> data = new HashMap<>();
        SendTemplateMsg.DataItem item1 = new SendTemplateMsg.DataItem("测试1");
        SendTemplateMsg.DataItem item2 = new SendTemplateMsg.DataItem("测试2");
        data.put("first",item1);
        data.put("remark",item2);
        msg.setData(data);
        msg.setTemplate_id("dEtdu9Qup-tQCt8vxVuhh4rahZ2sZMOEyI5W_KsZrgg");
        String msgJson = "";
        try {
            msgJson = JsonUtils.objectToJson(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msgJson);
        String result = HttpUtils.post(String.format(Constants.URL_TEMPLATE_SEND, WxUtils.getAccessToken()), msgJson);
        MsgResult msgResult = WxUtils.getWxResult(result,MsgResult.class);
        if(!msgResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(msgResult.getErrcode()));
            if(WxUtils.isAccessTokenErr(msgResult)){
                sendTemplateMsg(openid);
            }else {

            }
        }
    }
    public static void sendTemplateMsg2(String openid){
//        om9ct1GJoXoxg0xZ4iUHlv1xOo6c
        SendTemplateMsg msg = new SendTemplateMsg();
        msg.setTouser(openid);
        Map<String, SendTemplateMsg.DataItem> data = new HashMap<>();
        SendTemplateMsg.DataItem item1 = new SendTemplateMsg.DataItem("标题");
        SendTemplateMsg.DataItem item2 = new SendTemplateMsg.DataItem("测试项值");
        SendTemplateMsg.DataItem item3 = new SendTemplateMsg.DataItem("测试备注");
        data.put("first",new SendTemplateMsg.DataItem("22日上午上门维修空调","#003399"));
        data.put("keyword1",new SendTemplateMsg.DataItem("家电维修"));
        data.put("keyword2",new SendTemplateMsg.DataItem("张三"));
        data.put("keyword3",new SendTemplateMsg.DataItem("13333332222"));
        data.put("keyword4",new SendTemplateMsg.DataItem("灵溪镇--城北社区--沿江村"));
        data.put("keyword5",new SendTemplateMsg.DataItem("沿江路55号"));
        data.put("keyword6",new SendTemplateMsg.DataItem("2018-11-30 23:23:23"));
        data.put("remark",new SendTemplateMsg.DataItem("【注意】上门前先电话联系","#003399"));
        msg.setData(data);
        msg.setTemplate_id("Bay3N1k_EL2MbgmSIsOfesORJI04rUVlq40ApaIUs4E");
        msg.setUrl("lol.qq.com");

        String msgJson = "";
        try {
            msgJson = JsonUtils.objectToJson(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msgJson);
        String result = HttpUtils.post(String.format(Constants.URL_TEMPLATE_SEND, WxUtils.getAccessToken()), msgJson);
        MsgResult msgResult = WxUtils.getWxResult(result,MsgResult.class);
        if(!msgResult.isSuccess()){
            System.out.println(WxCodeEnum.getMsgByCode(msgResult.getErrcode()));
            if(WxUtils.isAccessTokenErr(msgResult)){
                sendTemplateMsg(openid);
            }else {

            }
        }
    }

    private void write(HttpServletResponse response,String xml){
        try {
            response.getWriter().print(xml);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
