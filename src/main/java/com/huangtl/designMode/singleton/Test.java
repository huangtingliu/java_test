package com.huangtl.designMode.singleton;

public class Test {

    public static void main(String[] args) {

        int i = 0;
        int max = 1000000000;
        long start = System.currentTimeMillis();

        while (i<max) {
            //饿汉实例
            Singleton1 s1 = Singleton1.getInstance();
            //Singleton1 s2 = Singleton1.getInstance();
            //if (s1 == s2) {
            //    System.out.println("s1和s2是同一个实例");
            //} else {
            //    System.out.println("s1和s2不是同一个实例");
            //}
            i++;
        }
        System.out.println("饿汉单例模式实例化"+i+"次速度："+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();

        i=0;
        while (i<max) {
            //new Thread(new Runnable() {
            //    @Override
            //    public void run() {
            //        //懒汉实例（错误版本测试）
            //        Singleton2 s = Singleton2.getInstance3();
            //    }
            //}).start();

            //懒汉实例
            Singleton2 s3 = Singleton2.getInstance();
            //Singleton2 s4 = Singleton2.getInstance();
            //if (s3 == s4) {
            //    System.out.println("s3和s4是同一个实例");
            //} else {
            //    System.out.println("s3和s4不是同一个实例");
            //}
            i++;
        }

        System.out.println("懒汉单例模式实例化"+i+"次速度："+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        i=0;
        while (i<max) {
            //IoDH单例模式
            Singleton3 s5 = Singleton3.getInstance();
            //Singleton3 s6 = Singleton3.getInstance();
            //if (s5 == s6) {
            //    System.out.println("s5和s6是同一个实例");
            //} else {
            //    System.out.println("s5和s6不是同一个实例");
            //}
            i++;
        }
        System.out.println("oDH单例模式实例化"+i+"次速度："+(System.currentTimeMillis()-start));
    }
}
