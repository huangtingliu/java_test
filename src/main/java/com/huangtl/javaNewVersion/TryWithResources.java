package com.huangtl.javaNewVersion;

import java.io.*;

/**
 * @Description try-with-resources java版本语法示例
 * @Author huangtl
 * @Date 2020/11/27 14:22
 **/
public class TryWithResources {

    /**
     * try-with-resources
     * @param args
     */
    public static void main(String[] args) throws Exception {

        //1.普通版本
        InputStream input = new FileInputStream(new File(""));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try{
            System.out.println(reader.readLine());
        }finally {
            if (reader != null) {
                reader.close();
            }
        }

        //2.放在try中，可以自动释放资源（java7支持）
        try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(input))){
            System.out.println(reader2.readLine());
        }

        try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(input));
             BufferedReader reader3 = new BufferedReader(new InputStreamReader(input));){
            System.out.println(reader2.readLine());
            System.out.println(reader3.readLine());
        }

        //3.jdk9才支持
        /*BufferedReader reader4 = new BufferedReader(new InputStreamReader(input));
        BufferedReader reader5 = new BufferedReader(new InputStreamReader(input));
        try (reader4;reader5){
            System.out.println(reader4.readLine());
            System.out.println(reader5.readLine());
        }*/
    }

}
