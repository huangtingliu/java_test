package com.huangtl.weixin.utils;

import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.bean.event.MenuEvent;
import com.huangtl.weixin.bean.msg.SendMsg;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessgaeUtils {

    /*将xml格式转化为map*/
    public static Map<String,Object> xmlToMap(HttpServletRequest request) throws Exception{
        Map<String,Object> map=new HashMap<>();

        SAXReader reader=new SAXReader();
        InputStream inputStream=request.getInputStream();
        Document doc=reader.read(inputStream);
        Element root=doc.getRootElement();//得到根节点
        List<Element> list=root.elements();//根节点下的所有的节点
        for(Element e:list){
            map.put(e.getName(),e.getData());
        }

        inputStream.close();
        return  map;
    }

    /*获取微信服务器发送过来的XML串*/
    public static String getXmlStr(HttpServletRequest request) throws Exception{
        //接受微信服务器发送过来的XML形式的消息
        InputStream in=request.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String sReqData="";
        String itemStr="";//作为输出字符串的临时串，用于判断是否读取完毕
        while((itemStr=reader.readLine())!=null){
            sReqData+=itemStr;
        }
        in.close();
        reader.close();

        return  sReqData;
    }

    /**
     * 消息对象转化为xml格式
     * @param message
     * @return
     */
    public static String objectToXml(SendMsg message){
        XStream xStream=new XStream();
        xStream.alias("xml", message.getClass());
        String xml = xStream.toXML(message);
//        String xml = xStream.toXML(message).replaceAll("\n","").replaceAll(" ","");
        System.out.println(xml.trim());
        return xml.trim();
    }

    /**
     * 获取微信通知消息类型
     * @param map
     * @return
     */
    public static String getMsgType(Map map){
        String msgType = null!=map.get("MsgType")?map.get("MsgType").toString():"";
        return msgType;
    }

    /**
     * 获取微信事件消息事件类型
     * @param map
     * @return
     */
    public static String getEventType(Map map){
        String eventType = null!=map.get("Event")?map.get("Event").toString():"";
        return eventType;
    }


    /**
     * 接收的微信消息转化为菜单事件消息对象
     * @param map
     * @return
     */
    public static MenuEvent getMenuEvent(Map map){
        try {
            String json = JsonUtils.objectToJson(map);
            MenuEvent event = JsonUtils.jsonToPojo(json, MenuEvent.class);
            return event;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MenuEvent();
    }

}
