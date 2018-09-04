package com.huangtl.proxy.staticProxy;

public class MyStaticImp implements MyStaticInterface {
    @Override
    public void sayHello() {
        System.out.println("hello static proxy");
    }
}
