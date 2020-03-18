//package com.huangtl.mongodb;
//
//import com.huangtl.idempotent.dao.OrderDao;
//import com.huangtl.utils.FileUtils;
//import com.mongodb.client.gridfs.GridFSFindIterable;
//import com.mongodb.client.gridfs.model.GridFSFile;
//import com.mongodb.gridfs.GridFSDBFile;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.gridfs.GridFsTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//public class localpicToMongodb {
//
//    @Autowired
//    private GridFsTemplate gridFsTemplate;
//    @Autowired
//    private OrderDao orderDao;
//
//    //同步数据库老人图片
////    @Scheduled(cron = "0 50 17 27 * *")
//    public void test1() throws IOException {
//        System.out.println("开始同步mongodb老人图片....");
//        ;
////        uploadFiles(new File("D:\\phpStudy\\PHPTutorial\\WWW\\vserve_tp\\Public\\Uploads"));
//        List<String> pics = orderDao.queryAllMemberPic();
//        for (String picurl : pics) {
//            try {
////                File file = new File("D:\\phpStudy\\PHPTutorial\\WWW\\vserve_tp"+picurl);
//                File file = new File("D:\\phpStudy\\PHPTutorial\\WWW\\vserve_tp"+picurl);
//                if(file.exists() && file.isFile()){
//                    uploadFiles(file);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//        }
//        System.out.println("同步成功");
//    }
//
////    @Scheduled(cron = "0 5 9 9 * *")
//    public void test() throws IOException {
//        System.out.println("开始同步mongodb老人图片....");
//        ;
////        uploadFiles(new File("D:\\pic"));
//        uploadFiles(new File("D:\\phpStudy\\PHPTutorial\\WWW\\vserve_tp\\Public\\Uploads"));
//        System.out.println("同步成功");
//    }
//
//    public  void uploadFiles(File file) throws IOException {
//        if(file.isFile() && FileUtils.isPicture(file.getName())){
//            //图片文件则保存
//            System.out.println("图片文件："+file.getAbsolutePath());
//            Resource rsFile = new FileSystemResource(file.getAbsolutePath());
//            String contentType = FileUtils.getContentType(FileUtils.getExtend(rsFile.getFilename()));
//            if(!isExist(rsFile.getFilename())) {
//                gridFsTemplate.store(rsFile.getInputStream(), rsFile.getFilename(), contentType);
//            }
//        }else if(file.isDirectory()){
//            System.out.println("文件夹："+file.getAbsolutePath());
//            if("thumbs".equals(file.getName())){
//                System.out.println("thumbs文件夹："+file.getAbsolutePath());
////            }else if(isGe("2018-03-28",file.getName())){
//            }else if(isGe("2018-06-28",file.getName())){
//                System.out.println("已迁移的目录");
//            } else {
//                File[] files = file.listFiles();
//                for (File f : files) {
//                    uploadFiles(f);
//                }
//            }
//        }
//    }
//
//    //判断是否big大于等于small
//    public boolean isGe(String big,String small){
//        big = big.replaceAll("-", "");
//        small = small.replaceAll("-", "");
//        try {
//            int bigNum = Integer.parseInt(big);
//            int smallNum =Integer.parseInt(small);
//            if(bigNum>=smallNum){
//                return true;
//            }
//        }catch (Exception e){
//            return false;
//        }
//        return false;
//    }
//
//    //文件是否已经存在
//    public boolean isExist(String filename){
//        Query q = new Query(Criteria.where("filename").is(filename));
////        List<GridFSDBFile> list = gridFsTemplate.find(q);
//        GridFSFile gridFSFile = gridFsTemplate.findOne(q);
//        if(null!=gridFSFile && StringUtils.isNotEmpty(gridFSFile.getFilename())){
//            System.out.println("已存在");
//            return true;
//        }
//        return false;
//    }
//
//    public static void main(String[] args) throws IOException {
////        Resource file = new FileSystemResource("D:\\pic\\2017-05-27\\0b3744c44f2c24f3ec5a0529558f960d.jpg");
////        System.out.println(file.getFilename());
//
////        Resource file = new FileSystemResource("D:\\pic\\2017-05-27");
////        uploadFiles(new File("D:\\\\pic"));
//
//        File f = new File("D://aaa");
//        System.out.println(f.exists());
//    }
//}
