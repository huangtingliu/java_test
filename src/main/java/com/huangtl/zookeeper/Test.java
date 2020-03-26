package com.huangtl.zookeeper;


import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        File file = new File("f://test.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            for (int i = 0; i < 100; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        String str = null;
                        try {
                            str = reader.readLine().substring(0);
                            if(null==str){
                                str="0";
                            }
                            System.out.println(str);
                            int num = Integer.parseInt(str);
                            writer.write(String.valueOf(num));
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            String str = reader.readLine();
            System.out.println(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }



        executorService.shutdown();
    }
}
