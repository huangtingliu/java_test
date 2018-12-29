package com.huangtl.weixin.controller;

import com.huangtl.weixin.enums.MsgType;
import com.huangtl.weixin.service.MsgService;
import com.huangtl.weixin.utils.MPUtils;
import com.huangtl.weixin.utils.MessgaeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信公众号交互控制层
 * 说明：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319
 *
 * checkToken：验证URL有效性成功后即接入生效，成为开发者
 *
 * 用户每次向公众号发送消息、或者产生自定义菜单、或产生微信支付订单等情况时，
 * 开发者填写的服务器配置URL将得到微信服务器推送过来的消息和事件，开发者可以依据自身业务逻辑进行响应，如回复消息。
 */
@Controller
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private MsgService msgService;


    /**
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319
     * 微信公众号token验证
         signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         timestamp	时间戳
         nonce	随机数
         echostr	随机字符串
     * @return
     */
//    @RequestMapping()
    @RequestMapping("/checkToken")
    @ResponseBody
    public void checkToken(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        boolean ok = MPUtils.checkToken( signature, timestamp, nonce, echostr);
        if(ok){
            response.getWriter().print(echostr);
            System.out.println("验证通过");
        }
    }

    /**
     * 接收消息
     * @param request
     * @param response
     */
    @RequestMapping()
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("接收到消息");

        Map<String, Object> map = MessgaeUtils.xmlToMap(request);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        MsgType msgType = MsgType.valueOf(MessgaeUtils.getMsgType(map).toUpperCase());
        switch (msgType){
            case EVENT:
                msgService.event(map,request,response);
                break;
            case TEXT:
                msgService.text(map,request,response);
                break;
            case IMAGE:
                break;
            case VOICE:
                break;
            case VIDEO:
                break;
            case MUSIC:
                break;
            case NEWS:
                break;
        }


        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            StringBuffer vales = new StringBuffer();
            for (String s : entry.getValue()) {
                vales.append(s);
                vales.append(",");
            }
            System.out.println(entry.getKey()+":"+vales);
        }

    }

    @RequestMapping("createMenu")
    @ResponseBody
    public String createMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if("vserve".equals(request.getParameter("code"))) {
            boolean success = MPUtils.createMenu();

            return success?"成功":"创建失败";
        }
        return "错误请求";
    }

    /**
     * 网页授权微信回调页面
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
     *
     * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     * code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/redirectUri")
    public String redirectUri(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //通过code换取网页授权access_token(与基础支持中的access_token不同)
        String code = request.getParameter("code");

        String state = request.getParameter("state");
        return "index";
    }
}
