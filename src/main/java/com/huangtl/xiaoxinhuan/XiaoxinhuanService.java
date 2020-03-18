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
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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
    private OrderDao orderDao;/*
    @Autowired
    private com.huangtl.mongodb.localpicToMongodb localpicToMongodb;*/

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
        //公司 lng:119.211747; lat:26.037612
        double lng = 119.211747;
        double lat = 26.037612;

        String address =getAddress(lng,lat);
//        String address ="福建省福州市海峡国际会展中心8号馆";
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

	/*获取旧平台工单、上传mongodb*/
    public void transferOrderImg(){
        List<Map> paramList = new ArrayList<>();

        List<Map> list = orderDao.queryOrderList();
        System.out.println("获取要新增的订单总数："+list.size()+"条");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentMillis = System.currentTimeMillis();
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

                        param.put("createDate", sdf.format(new Date(currentMillis + i*1000)));
                        paramList.add(param);
//                        File file = new File("D:\\phpStudy\\PHPTutorial\\WWW\\vserve_tp"+path);
//                        if(file.exists() && file.isFile()){
//                            try {
//                                localpicToMongodb.uploadFiles(file);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }
            }

        }

        System.out.println("新增订单图片数量："+paramList.size()+"条");
//        orderDao.insertOrderImg(paramList);
    }

    /*将旧平台工单图片临时表数据添加到新平台工单图片表*/
    public void insertImgByOrderImg(){
        List<Map> paramList = new ArrayList<>();

        List<Map> list = orderDao.queryOrderImgList();
        System.out.println("获取要新增的订单总数："+list.size()+"条");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentMillis = System.currentTimeMillis();
        for (Map map : list) {

            String orderId = MapUtils.getString(map,"id");
            String service_img = MapUtils.getString(map,"imgs");
            if(null!=service_img && !"".equals(service_img) ){

                String[] imgs = service_img.split(",");
                    int len = imgs.length;
                    for (int i=0;i<len;i++){
                        String path = imgs[i];
                        if(StringUtils.isNotEmpty(path)){
                            Map param = new HashMap();
                            param.put("orderId",orderId);
                            //格式：/Public/Uploads/2019-01-22/4c8227122c076d6c1d19407a772756d9.jpg
                            //需转成oss路径/prod/pic/old/xx.jpg

                            param.put("imagePath",getOssPath(path));
                            Date date = new Date(currentMillis + i * 1000);
                            param.put("createDate", sdf.format(date));
                            param.put("sortDate",date.getTime());
                            paramList.add(param);
                        }
                    }
            }

        }

        System.out.println("新增订单图片数量："+paramList.size()+"条");
        orderDao.insertOrderImg(paramList);
        System.out.println("新增订单图片完成");
    }

    //图片路径转OSS路径 /Public/Uploads/2019-01-22/xx.jpg =》 prod/pic/2019-01-22/xx.jpg
    private static String getOssPath(String path){
//        String ossPath = path.substring(27,path.length());
        String ossPath = path.substring(16,path.length());
        ossPath = "prod/pic/"+ossPath;
        return ossPath;
    }

    public static void main(String[] args) {
        System.out.println(getOssPath("/Public/Uploads/2019-01-22/xx.jpg"));
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

    //替换话务为建宁水南中心的
    public void transferCallOrg(){
        List<Map> archiveIds = orderDao.queryRandArchiveIds();//获取需要更新的老人
        List<String> callIds = orderDao.queryRandCallIds(archiveIds.size());


            for (int i = 0; i < archiveIds.size(); i++) {
                Map map = archiveIds.get(i);
                if(callIds.size()>i) {
                    String callId = callIds.get(i);
                    map.put("callId", callId);
                    System.out.println(map);
                    orderDao.updateCallOrg(map);
                }
            }

    }



    /*一键通部分*/
    public void yjtRegainCallInfo(){
        long startTime = System.currentTimeMillis();
        //获取所有老人列表已电话为Key，id为value保存
        List<Map> list = orderDao.queryYjtMemberList();

        System.out.println("获取老人总数："+list.size());
        Jedis jedis = RedisTest.getJedis();
        int count = 0;
        //        亲属列表
        List<Map> relationList = orderDao.queryYjtMemberRelationList();

        System.out.println("获取亲属总数："+relationList.size());
        for (Map map : relationList) {
            String id = MapUtils.getString(map, "id");
            String archives_id = MapUtils.getString(map, "archives_id");
            String member_phone = MapUtils.getString(map, "member_phone");
            if(StringUtils.isNotEmpty(member_phone)){
                jedis.set(member_phone,archives_id);
                jedis.set(member_phone+"_relation_id",id);
                jedis.set(member_phone+"_care_phone_type","3");
                jedis.expire(member_phone,1800);
                jedis.expire(member_phone+"_relation_id",1800);
                jedis.expire(member_phone+"_care_phone_type",1800);
                count++;
                System.out.println(count);
            }
        }

        for (Map map : list) {
            String id = MapUtils.getString(map, "id");
            String phone1 = MapUtils.getString(map, "phone1");
            String sim_code = MapUtils.getString(map, "sim_code");
            if(StringUtils.isNotEmpty(phone1)){
                jedis.set(phone1,id);
                jedis.set(phone1+"_care_phone_type","2");
                jedis.expire(phone1,1800);
                jedis.expire(phone1+"_care_phone_type",1800);
                count++;
                System.out.println(count);
            }
            if(StringUtils.isNotEmpty(sim_code)){
                jedis.set(sim_code,id);
                jedis.set(sim_code+"_care_phone_type","1");//手环类型
                jedis.expire(sim_code,1800);
                jedis.expire(sim_code+"_care_phone_type",1800);
                count++;
                System.out.println(count);
            }
        }


        System.out.println("保存电话key总数："+count);

//        2.查询备份的所有话务单，根据电话在上面的缓存中查询老人id,跟其他信息一起保存到通话表
        List<Map> backupList = orderDao.queryYjtBackupCallList();
        System.out.println("查询备份话务单总数："+backupList.size());
        count = 0;
        for (Map map : backupList) {
            String in_out = MapUtils.getString(map, "in_out");
            String phone_from = MapUtils.getString(map, "phone_from");
            String phone_to = MapUtils.getString(map, "phone_to");
            if("1".equals(in_out)){
                //呼入则根据呼入号码获取老人id
                String id = jedis.get(phone_from);
                String care_phone_type = jedis.get(phone_from+"_care_phone_type");
                map.put("archives_id",id);
                map.put("care_phone_type",care_phone_type);
                map.put("phone",phone_from);
                map.put("call_object_type","0");
            }else{
                //呼出则根据呼出号码获取老人id
                String id = jedis.get(phone_to);
                String care_phone_type = jedis.get(phone_to+"_care_phone_type");
                map.put("archives_id",id);
                map.put("care_phone_type",care_phone_type);
                map.put("phone",phone_to);
                String call_object_type = "0";
                if("3".equals(care_phone_type)){
                    call_object_type = "2";//亲属
                    String relation_id = jedis.get(phone_to+"_relation_id");
                    map.put("relation_id",relation_id);
                }
                map.put("call_object_type",call_object_type);
            }
            count++;
            System.out.println("话务"+count);
        }

//        3.插入新平台话务表
        inserPart(backupList,0,1000);
//        orderDao.insertYjtBackupCallList(backupList.subList(0,10000));
//        orderDao.insertYjtBackupCallList(backupList.subList(10000,20000));
//        orderDao.insertYjtBackupCallList(backupList.subList(20000,30000));
//        orderDao.insertYjtBackupCallList(backupList.subList(30000,40000));
//        orderDao.insertYjtBackupCallList(backupList.subList(40000,50000));
//        orderDao.insertYjtBackupCallList(backupList.subList(50000,60000));
//        orderDao.insertYjtBackupCallList(backupList.subList(60000,backupList.size()));

        long endTime = System.currentTimeMillis();
        long costTime = endTime-startTime;
        System.out.println("花费"+costTime+"ms（"+(costTime/1000)/60+"分钟)");
    }

    int num = 0;
    private void inserPart(List<Map> list,int from,int to){
        int size = 1000;
            System.out.println(from+","+to);
        if(list.size()>=to) {
            orderDao.insertYjtBackupCallList(list.subList(from, to));
        }else{
            orderDao.insertYjtBackupCallList(list.subList(from,list.size()));
        }
        if(list.size()>=(to+size)){
            inserPart(list,to,to+size);
        }else{
            orderDao.insertYjtBackupCallList(list.subList(to,list.size()));
            System.out.println(to+","+list.size());
        }

    }

    //恢复一键通老人最新关怀记录
    public void yjtRegainCareInfo(){
        long startTime = System.currentTimeMillis();

        //获取一键通数据库迁移前的通话记录，按老人分组取最新的通话时间（2019-07-05之前）
//        List<Map> list = orderDao.queryYjtMemberCareCallList();
        List<Map> list = null;
        System.out.println("获取老人最新通话总数："+list.size());
        Jedis jedis = RedisTest.getJedis();
        int count = 0;
        for (Map map : list) {
            String call_id = MapUtils.getString(map, "call_id");
            String archives_id = MapUtils.getString(map, "archives_id");
            String call_time = MapUtils.getString(map, "call_time");
            String user_id = MapUtils.getString(map, "user_id");
            String watch_call_id = MapUtils.getString(map, "watch_call_id");
        }
        System.out.println("保存电话key总数："+count);

//        3.插入新平台关怀表

        long endTime = System.currentTimeMillis();
        long costTime = endTime-startTime;
        System.out.println("花费"+costTime+"ms（"+(costTime/1000)/60+"分钟)");
    }
}
