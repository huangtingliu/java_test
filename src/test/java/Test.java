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

        StringBuffer departidSb = new StringBuffer();
        departidSb.append("123123");
        departidSb.insert(0,"(");
        departidSb.append(")");
        System.out.println(departidSb.toString());
    }
}
