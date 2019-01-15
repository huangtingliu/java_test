package com.huangtl.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http/https请求封装类
 */
public class HttpUtils {

    /**
     * post方法
     * @param url 地址
     * @param params 参数
     * @return
     */
    public static String post(String url,Map<String,String> params){

        HttpPost httpPost = new HttpPost(url);

        //封装参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request(httpPost);
    }

    /**
     * post方法
     * @param url 地址
     * @param params 参数
     * @return
     */
    public static String post(String url,String params) {

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-type","text/html;charset=utf-8");
        httpPost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));

        return request(httpPost);
    }



    /**
     * get请求
     * @param url 地址
     * @param params 参数
     * @return
     */
    public static String get(String url,Map<String,String> params){

        //封装参数
        StringBuffer sb = new StringBuffer();
        boolean hasNoParam = true;
        if(url.indexOf("?")!=-1){
            hasNoParam = false;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(hasNoParam){
                sb.append("?");
                hasNoParam=false;
            }else{
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return get(url+sb.toString());
    }

    public static String get(String url){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        return request(httpGet);
    }

    public static String request(HttpRequestBase req){

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;
        try {

            //执行请求，获取返回结果
            response = httpclient.execute(req);

            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String result = inputStreamToString(entity.getContent());

            EntityUtils.consume(entity);
            System.out.println("请求结果："+result);

            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 输入流转字符串方法1
     * @param inputStream
     * @return
     */
    private static String inputStreamToString(InputStream inputStream){
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = null;
            str = result.toString(StandardCharsets.UTF_8.name());
            return str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 输入流转字符串方法2
     * @param inputStream
     * @return
     */
    private static String inputStreamToString2(InputStream inputStream){
        try {
            StringBuilder sb = new StringBuilder();
            String line;

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String str = sb.toString();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
