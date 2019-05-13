package com.huangtl.proxy;

import com.huangtl.proxy.dynamic.MyImp;
import com.huangtl.proxy.dynamic.MyInterface;
import com.huangtl.proxy.dynamic.MyProxy;
import com.huangtl.proxy.dynamic.ProxyUtil;
import com.huangtl.proxy.staticProxy.MyStaticProxy;

import java.lang.reflect.Proxy;

public class Test {

    public static void main1(String[] args) {

        //静态代理测试
        System.out.println("测试静态代理=====================");
        MyStaticProxy myStaticProxy = new MyStaticProxy();
        myStaticProxy.sayHello();

        //动态代理测试
        System.out.println("测试动态代理=====================");
        //创建代理类，并传入代理的对象类型
        MyProxy myProxy = new MyProxy(MyImp.class);

        //Proxy.newProxyInstance三个参数分别为：
        //一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
        //一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了
        //一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
        MyInterface myInterface = (MyInterface) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{MyInterface.class}, myProxy);
        //MyInterface myInterface = (MyInterface) Proxy.newProxyInstance(Test.class.getClassLoader(), MyImp.class.getInterfaces(), myProxy);
        System.out.println(myInterface.getClass().getName());
        myInterface.sayHello();

        //测试封装的代理工具类
        System.out.println("测试封装类动态代理======================");
        MyImp myImp = new MyImp();
        MyInterface proxy = new ProxyUtil<MyInterface>(myImp).getProxy();
        proxy.sayHello();

    }
}
