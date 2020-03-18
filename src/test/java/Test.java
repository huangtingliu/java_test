import com.huangtl.utils.HttpUtils;
import com.huangtl.utils.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

    public static void main(String[] args) {
//        A ab = new B();
//        ab = new B();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        int hour = d.getHours() - 24;
        d.setHours(hour);
        System.out.println(sdf.format(d));*/

        //字符串逗号测试
        /*String personnelCategories = "abcd,";
        System.out.println("检查字符串："+personnelCategories);
        System.out.println("lastIndexOf:"+personnelCategories.lastIndexOf(","));
        System.out.println("length:"+personnelCategories.length());
        if(personnelCategories.lastIndexOf(",")==personnelCategories.length()-1){
            System.out.println("最后一个是逗号，去除逗号后字符串：");
            personnelCategories = personnelCategories.substring(0,personnelCategories.length()-1);
            System.out.println(personnelCategories);
        }else{
            System.out.println("最后一个不是逗号");
        }*/
        //二进制
//        String personnelCategoriesBin = Integer.toBinaryString(8);
////        personnelCategoriesBin="";
//        System.out.println("二进制补零前："+personnelCategoriesBin);
//        //不足10位要补零
//        int fillNum = 10-personnelCategoriesBin.length();
//        if(fillNum>0){
//            for (int i=0;i<fillNum;i++){
//                personnelCategoriesBin = "0"+personnelCategoriesBin;
//            }
//        }
//        System.out.println("二进制补零后："+personnelCategoriesBin);

//        StringBuffer departidSb = new StringBuffer();
//        departidSb.append("123123");
//        departidSb.insert(0,"(");
//        departidSb.append(")");
//        System.out.println(departidSb.toString());

//        TestBean t = new TestBean();
//        if(null !=t.getInOut() && 1 == t.getInOut()){
//            System.out.println("通过");
//        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//            System.out.println(calcDiff(sdf.parse("2019-06-06 11:26:04"),sdf.parse("2019-06-06 11:26:38")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Map<String, String> map = new HashMap<>();
//        map.put("entId","7768688345");
//        map.put("callId","3rCCUBe9-1560218940750");
//        HttpUtils.post("http://183.134.110.183:9005/cds/call/detail",map);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        System.out.println(sdf.format(new Date()));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        System.out.println(month);

//        String json = "[{\"cdrId\":63836104,\"callType\":1,\"src\":\"057768688345\",\"dst\":\"18359105940\",\"callTime\":\"2019-06-12 15:03:09\",\"hangupTime\":\"2019-06-12 15:04:19\",\"ringLength\":24526,\"ivrLength\":0,\"queueLength\":0,\"dailLength\":45472,\"recordName\":\"/var/spool/asterisk/monitor/1560322989_1560322989.73034948_fujianweishang.wav\",\"agentId\":\"777628001\",\"entId\":7768688345,\"callId\":\"1560322989.73034948\",\"entName\":\"fujianweishang\",\"hangupCause\":\"Normal Clearing\",\"city\":\"绂忓窞甯�\",\"rcmm\":\"183.134.110.170\",\"dailStatus\":1}]";
//        List<Map> maps = JsonUtils.jsonToList(json, Map.class);
//        System.out.println(maps);
        
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        List<String> strings = list.subList(0, 2);
        List<String> strings1 = list.subList(2, 4);
        List<String> strings2 = list.subList(4, list.size());

        System.out.println("");
    }

    public static long calcDiff(Date dateSmall, Date dateBig){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = dateBig.getTime() - dateSmall.getTime();
        // 计算差多少天
//        long day = diff / nd;
        // 计算差多少小时
//        long hour = diff % nd / nh;
        // 计算差多少分钟
//        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        return sec;
    }
}
