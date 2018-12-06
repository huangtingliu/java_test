package com.huangtl.weixin.scheduler;

import com.huangtl.weixin.WxUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenScheduler {


    /**
     * 定时刷新access_token
     * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     */
    @Scheduled(cron = "0 0 0 0 0 0 0")
    public void refreshAccessToken(){
        WxUtils.getNewAccessToken();
    }
}
