package com.huangtl.mysql.bak;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用mysqldump备份mysql数据库
 */
public class MysqldumpTest {

    /**
     * Java代码实现MySQL数据库导出
     *
     * @author GaoHuanjie
     * @param hostIP MySQL数据库所在服务器地址IP
     * @param userName 进入数据库所需要的用户名
     * @param password 进入数据库所需要的密码
     * @param savePath 数据库导出文件保存路径
     * @param fileName 数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @return 返回true表示导出成功，否则返回false。
     */
    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if(!savePath.endsWith(File.separator)){
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            String path1 ="D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin\\";
            //mysqldump --no-defaults -hlocalhost -uroot -proot test > h://test.sql 命令行运行这个可以成功，网上很多不加--no-defaults参数没办法运行
            //mysqldump [OPTIONS] database [tables] 只备份表数据，也就是备份的sql里没有创建数据库语句
            //mysqldump [OPTIONS] --databases [OPTIONS] DB1 [DB2 DB3...]  备份的sql有创建数据库语句
            //mysqldump [OPTIONS] --all-databases [OPTIONS] 会备份所有的数据库,也就是sql里会创建所有的数据库
            //Process process = Runtime.getRuntime().exec(path1+"mysqldump --no-defaults -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 --databases " + databaseName);
            //Process process = Runtime.getRuntime().exec(path1+"mysqldump --no-defaults -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);
            Process process = Runtime.getRuntime().exec(path1+"mysqldump --no-defaults -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 --all-databases" );
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                printWriter.println(line);
            }
            printWriter.flush();
            if(process.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main1(String[] args){
        try {
            String filePath = new SimpleDateFormat("yyyy-MM-ddHHmmss").format(new Date())+".sql";
            if (exportDatabaseTool("localhost", "root", "root", "H:/backupDatabase", filePath, "test")) {
                System.out.println("数据库成功备份！！！");
            } else {
                System.out.println("数据库备份失败！！！");
            }

            //backup();

            //restore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原
     */
    public static void restore() {
        try {
            Runtime runtime = Runtime.getRuntime();
            //Process process = runtime
            //        .exec("D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin -hlocalhost -uroot -proot --default-character-set=utf8 "
            //                + databaseName);
            //String path1 ="D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin\\";
            Process process = runtime
                    .exec("D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin\\mysql.exe -hlocalhost -uroot -proot --default-character-set=utf8 "
                    );
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("H:\\backupDatabase\\2018-08-02.sql"), "utf-8"));
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            // System.out.println(str);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                    "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 另外一种备份
     */
    public static void backup() {
        try {
            Runtime rt = Runtime.getRuntime();

            // 调用 调用mysql的安装目录的命令
            Process child = rt.exec("D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin\\mysqldump --no-defaults -h localhost -uroot -proot test");
            //Process child = rt.exec("D:\\dev\\mysql\\mysql\\MySQL Server 5.6\\bin\\mysqldump --no-defaults -h localhost -uroot -proot test");
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码

            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // 要用来做导入用的sql目标文件：
            FileOutputStream fout = new FileOutputStream("H:\\test.sql");
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
