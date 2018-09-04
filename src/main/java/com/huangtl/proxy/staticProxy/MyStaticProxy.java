package com.huangtl.proxy.staticProxy;

public class MyStaticProxy implements MyStaticInterface {

    MyStaticInterface target;

    @Override
    public void sayHello() {
        target = new MyStaticImp();
        before();
        target.sayHello();
        after();
    }

    private void before(){
        System.out.println("before");
    }
    private void after(){
        System.out.println("after");
    }
}
