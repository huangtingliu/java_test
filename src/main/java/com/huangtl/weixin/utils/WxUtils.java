package com.huangtl.weixin.utils;


import com.huangtl.utils.JsonUtils;
import com.huangtl.weixin.bean.result.WxResult;

import java.util.Arrays;

public class WxUtils {

    /**
     * 获取请求结果封装类
     * @param result
     * @return
     */
    public static WxResult getWxResult(String result) {
        WxResult wxResult = new WxResult();
        try {
            wxResult = JsonUtils.jsonToPojo(result, WxResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxResult;
    }
    /**
     * 获取请求结果封装类
     * @param result
     * @return
     */
    public static <T> T getWxResult(String result,Class<T> clazz) {
        T t = null;
        try {
            t = JsonUtils.jsonToPojo(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 字典序排序
     * @return
     */
    public static String sort(String token,String timestamp,String nonce){
        String[] arr = { token, timestamp, nonce };
        Arrays.sort(arr); // 字典序排序
        String str = arr[0] + arr[1] + arr[2];
        return str;
    }

}
