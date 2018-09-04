package com.huangtl.designMode.singleton;

/**
 * 饿汉模式
 * 类加载时就创建了，资源浪费
 * 不会出现创建多个单例对象的情况，可确保单例对象的唯一性
 */
public class Singleton1 {

    //1.私有化构造方法，防止外部创建实例
    private Singleton1(){
        System.out.println("初始化Singleton1");
    }

    //2.创建私有化实例
    private static Singleton1 singleton1 = new Singleton1();

    //3.创建提供实例方法
    public static Singleton1 getInstance(){
        return singleton1;
    }
}
