package com.huangtl.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WavToMp3 {
    /**
     * 将上传的录音转为mp3格式
     * @param sourcePath 文件的相对地址
     */
    public static void ToMp3(String sourcePath){
        File file = new File(sourcePath);
        String targetPath = sourcePath.substring(0,sourcePath.lastIndexOf("."))+".mp3";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start=System.currentTimeMillis();
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
           //Process p=run.exec("D:\\ffmpeg-20180521-c24d247-win64-static\\bin\\ffmpeg -f g729a -i "+sourcePath+" -acodec libmp3lame -ab 8 -ac 1 -ar 8000 -f mp3 -y "+targetPath);
           // Process p=run.exec("D:\\\\dev\\\\ffmpeg\\\\ffmpeg-20180521-c24d247-win64-static\\\\bin\\\\ffmpeg -f g729 -i "+sourcePath+" -acodec libmp3lame "+targetPath);
            //File newFile = new File("H:\\demo\\wav2mp3\\"+targetPath);
            Process p=run.exec("D:\\dev\\ffmpeg\\ffmpeg-20180521-c24d247-win64-static\\bin\\ffmpeg -f g729 -i "+sourcePath+" -ar 8000 -acodec libmp3lame -y "+targetPath);
            //Process p=run.exec("D:\\dev\\ffmpeg\\ffmpeg-20180521-c24d247-win64-static\\bin\\ffmpeg -f g729 -i "+sourcePath+" -y "+targetPath);
            ////释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            long end=System.currentTimeMillis();
            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
            //删除原来的文件
           /* if(file.exists()){
            file.delete();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
    }
    public static void main1(String[] args) throws IOException {

        byte[] bytes = getContent("H:\\demo\\wav2mp3\\20180525112503946.wav");
        File file = new File("H:\\demo\\wav2mp3\\20180525112503946.wav");
        file.delete();

        FileOutputStream outFile = new FileOutputStream("H:\\demo\\wav2mp3\\wav2mp3.wav");

        for (int i=44;i<bytes.length;i++) {
            //System.out.println(num+":"+aByte);
            outFile.write(bytes[i]);
        }

        outFile.close();

    }




    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            //return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

}
