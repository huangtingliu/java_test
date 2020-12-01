package com.huangtl;

import com.huangtl.kml.KmlProperty;
import com.huangtl.point.simplify.LatLng;
import com.huangtl.point.simplify.Test;
import com.huangtl.xiaoxinhuan.XiaoxinhuanService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@EnableScheduling
@MapperScan("com.huangtl.*.dao")
//@EnableTransactionManagement
@RestController
@SpringBootApplication
public class Application {

    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    private XiaoxinhuanService xiaoxinhuanService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }

    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/addOrder")
    @ResponseBody
    public String hello1(){
        xiaoxinhuanService.insertImgByOrderImg();//迁移工单图片oss
//        xiaoxinhuanService.transferOrderImg();//迁移工单图片mongodb
//        xiaoxinhuanService.transferCallInfo();//迁移话务单
        return "hello world";
    }

    //替换话务为建宁水南中心的
    @RequestMapping("/transfer")
    @ResponseBody
    public String transfer(){
//        xiaoxinhuanService.transferCallOrg();
        return "hello world";
    }


    @RequestMapping("/yjtRegainCallInfo")
    @ResponseBody
    public String yjtRegainCallInfo(){
//        xiaoxinhuanService.yjtRegainCallInfo();
        return "hello world";
    }

   /* @CrossOrigin(origins = "*")
    @RequestMapping("/points")
    @ResponseBody
    public LatLng[] points(double t){
        return Test.getSimplifiedPoints(t);
    }*/

    @CrossOrigin(origins = "*")
    @RequestMapping("/points")
    @ResponseBody
    public KmlProperty points(double t){
        return Test.getPoints(t);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/allpoints")
    @ResponseBody
    public LatLng[] getPoints(){
        return Test.getPoints();
    }
}
