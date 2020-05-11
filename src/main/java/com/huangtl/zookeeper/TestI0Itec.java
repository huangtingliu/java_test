package com.huangtl.zookeeper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestI0Itec {

    public static void main(String[] args) throws IOException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(100);
        ExecutorService executorService = Executors.newCachedThreadPool();

        Path path = Paths.get("f://test.txt");
        if(!Files.exists(path)){
            Files.createFile(path);
        }

        DistributedLock_I0Itec lock = new DistributedLock_I0Itec();
        for (int i = 0; i < 100; i++) {
            executorService.submit(()->{
                Node node = lock.lock();
                try {
                    String str = Files.lines(path).findFirst().orElse("0");
                    int num = Integer.parseInt(str);
                    num++;
                    Files.write(path, String.valueOf(num).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock(node);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        String str = Files.lines(path).findFirst().orElse("0");
        System.out.println("结果："+str);
    }

}
