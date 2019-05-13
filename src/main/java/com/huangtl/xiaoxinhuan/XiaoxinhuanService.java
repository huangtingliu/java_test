package com.huangtl.xiaoxinhuan;

import com.huangtl.idempotent.dao.OrderDao;
import com.huangtl.redis.RedisTest;
import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;
import com.huangtl.xiaoxinhuan.dao.XiaoDao;
import com.huangtl.xiaoxinhuan.entity.Location;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @Author: Archer
 * @Description: 孝心环接口
 * @Date: 2018/10/24 13:33
 */

@Service
public class XiaoxinhuanService {
	
    public final static String url = "http://openapi.mini361.com";
    public final static String key = "c9b5b882dcab6430dde8efb2b6bdca79";

    @Autowired
    private XiaoDao xiaoDao;
    @Autowired
    private OrderDao orderDao;

//    @Scheduled(cron = "0 0/1 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void ss(){
        JSONObject tracking = Tracking();
        String State = tracking.getString("State");

        if(!"0".equals(State)){

            System.out.println("定位返回不是0");
            return ;
        }

        JSONObject object = tracking.getJSONObject("Item");

        double lng = object.getDouble("Longitude");
        double lat = object.getDouble("Latitude");

        String lastTimeStr = object.getString("LastCommunication");
        JSONObject jsonObject = addressInfo(lat, lng);
        String AddState = jsonObject.getString("State");
        if(!"0".equals(AddState)){

            System.out.println("经纬度转地址返回不是0");
            return ;
        }
        String address = jsonObject.getString("Address");
        System.out.println(address);

//        ========写死经纬度部分=======
        Location location = new Location();
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAddress(address);


        orderDao.updateLocation(location);
//        xiaoDao.updateLocation(location);
    }

    //写死
//    @Scheduled(cron = "0 0/1 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void upLoc(){
        double lng = 119.366619;
        double lat = 26.026583;

//        String address =getAddress(lng,lat);
        String address ="福建省福州市海峡国际会展中心8号馆";
        Location location = new Location();
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAddress(address);

        orderDao.updateLocation(location);
    }

    private String getAddress(double lng,double lat){
        try {

            JSONObject jsonObject = addressInfo(lat, lng);
            String AddState = jsonObject.getString("State");
            if(!"0".equals(AddState)){

                System.out.println("经纬度转地址返回不是0");
                return "";
            }
            String address = jsonObject.getString("Address");
            System.out.println(address);
            return address;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

	public static JSONObject Tracking() {
//        String Imei = "864891030395133";
        String Imei = "864891030389107";
	    String maptype = "Tencent";
		// TODO Auto-generated method stub
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
        JSONObject jsonResult = null;
        String value="";
        String data="";
        //Imei="864891030023786";
       /* String MapType="Baidu";*/
        String MapType=maptype;
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        map.put("Imei",Imei);
        map.put("MapType", maptype);
        String sign=GetSign(map);
        String  jsonstr = "{\"MapType\": \"" + MapType + "\",";
        jsonstr += "\"Imei\":" + Imei + ",";
        jsonstr += "\"Sign\":\"" + sign + "\"}";
        HttpPost method = new HttpPost(url+"/api/Location/Tracking");
		
        try {
            if (null != jsonstr) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonstr, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            //url = URLDecoder.decode(url+"/api/Location/Tracking", "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    str = EntityUtils.toString(result.getEntity());
                    System.out.println(str);

                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.fromObject(str);
                    /*Iterator<String> it = jsonResult.keys();
                    while(it.hasNext()){
                    // 获得key
                       // String key = it.next();
                        value = jsonResult.getString("Item");
                        System.out.println("key: "+key+",value:"+value);
                        return value;
                    }*/
                } catch (Exception e) {
                    //logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            //logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
	}


	
    //获取签名
    public static String GetSign(Map map){
        String paramsStr=handelMap(map);
        //System.out.println(MD5Util.MD5EncodeGBK(paramsStr).toUpperCase());
        return MD5Util.MD5EncodeGBK(paramsStr).toUpperCase();
    }
    
    
    //map->字符串
    public static String handelMap(Map<String,Object> map){
        String paramsStr="";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (paramsStr==""){
                paramsStr+=entry.getKey()+"="+entry.getValue();
            }else{
                paramsStr+="&"+entry.getKey()+"="+entry.getValue();
            }
        }

        return paramsStr+"&key="+key;
    }
    
    
	public static JSONObject addressInfo(Double lat ,Double lng) {
		// TODO Auto-generated method stub
		
		
		Map<String,Object> paras=new LinkedHashMap<String,Object>();
		

        String utl=url+"/api/Location/Address";
        
        paras.put("Lat", lat);
		
		paras.put("Lng", lng);
		
		String sign=GetSign(paras);
		
		paras.put("sign", sign);
		
		
        HttpPost httpPost = new HttpPost(utl);

        httpPost.addHeader("Content-type","application/json;charset=utf-8");
        try {
        	
        	String StringJson = JsonUtils.objectToJson(paras);
        	
			httpPost.setEntity(new StringEntity(StringJson, Charset.forName("UTF-8")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String request = HttpUtils.request(httpPost);
        JSONObject jsonResult = JSONObject.fromObject(request);
        
		return jsonResult;
	}

    public void transferOrderImg(){
        List<Map> paramList = new ArrayList<>();

        List<Map> list = orderDao.queryOrderList();
        System.out.println("获取要新增的订单总数："+list.size()+"条");
        for (Map map : list) {

            String task_id = MapUtils.getString(map,"task_id");
            String service_img = MapUtils.getString(map,"service_img");
            String service_imgtags = MapUtils.getString(map,"service_imgtags");
            if(null!=service_img && !"".equals(service_img) && null!=service_imgtags){

                String[] imgs = service_img.split(",");
                String[] tags = service_imgtags.split(",");
                if(imgs.length==tags.length){

                    int len = imgs.length;
                    for (int i=0;i<len;i++){
                        Map param = new HashMap();
                        param.put("orderId",task_id);
                        String path = imgs[i];
                        String tag = tags[i];
                        if(tag.equals("服务前")){
                            param.put("imageType","2");
                        }else if(tag.equals("服务中")){
                            param.put("imageType","3");
                        }else if(tag.equals("服务后")){
                            param.put("imageType","4");
                        }
                        param.put("imagePath",path);
                        paramList.add(param);
                    }
                }
            }

        }

        System.out.println("新增订单图片数量："+paramList.size()+"条");
        orderDao.insertOrderImg(paramList);
    }

    /*迁移话务单*/
    public void transferCallInfo(){
        long startTime = System.currentTimeMillis();
//        1.获取所有老人id和其所属新平台org_id，保存Map<老人id,org_id>（因为话务表关联老人表查太久了，所以用java关联）
        Map<String,String> cache = new HashMap();
        List<Map> list = orderDao.queryMemberList();
        System.out.println("获取老人总数："+list.size());
        Jedis jedis = RedisTest.getJedis();
        int count = 0;
        for (Map map : list) {
            String mi_id = MapUtils.getString(map, "mi_id");
            String org_id = MapUtils.getString(map, "org_id");
            if(StringUtils.isNotEmpty(org_id)){
                jedis.set(mi_id,org_id);
                jedis.expire(mi_id,1800);
                count++;
                System.out.println(count);
//                cache.put(mi_id,org_id);
            }
        }
        System.out.println("保存老人id:所属照料中心总数："+count);

//        2.查询所有话务单，并且新增org_id字段，根据第一步的Map取
        List<Map> callInfoList = orderDao.queryOldCallInfo();
        System.out.println("查询话务单总数："+callInfoList.size());
        int noMemberNum =0;
        count = 0;
        for (Map map : callInfoList) {
            String contacts_id = MapUtils.getString(map, "contacts_id");
            String org_id = "";
            if(StringUtils.isEmpty(contacts_id)){
                noMemberNum++;
            }else{
//                org_id = cache.get(contacts_id);
                org_id = jedis.get(contacts_id);
            }
            map.put("org_id",org_id);
            count++;
            System.out.println("话务"+count);
        }
        System.out.println("没有老人的话务单总数："+noMemberNum);

//        3.插入新平台话务表
        orderDao.insertCallInfo(callInfoList);

        long endTime = System.currentTimeMillis();
        long costTime = endTime-startTime;
        System.out.println("花费"+costTime+"ms（"+(costTime/1000)/60+"分钟)");
    }
	
	
}
