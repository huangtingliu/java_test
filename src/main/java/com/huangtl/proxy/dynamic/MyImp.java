package com.huangtl.proxy.dynamic;

public class MyImp implements MyInterface {
    @Override
    public void sayHello() {
        System.out.println("hello proxy");
    }
}
