package com.huangtl.idempotent.controller;

import com.huangtl.idempotent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/queryOrder")
    @ResponseBody
    public List queryOrder(){
        List list = orderService.queryList();
        return list;
    }


    @RequestMapping("/sendOrder")
    @ResponseBody
    public String  sendOrder(String orderId){

        for (int i=0;i<6;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String flag = orderService.sendOrder2(orderId);
                }
            }).start();
        }
        return "";
    }

    @RequestMapping("/sendOrderByVersion")
    @ResponseBody
    public String  sendOrderByVersion(String orderId){
        for (int i=0;i<6;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String flag = orderService.sendOrder3(orderId);
                }
            }).start();
        }
        return "";
    }


}
