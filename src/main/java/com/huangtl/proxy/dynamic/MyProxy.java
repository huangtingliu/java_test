package com.huangtl.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类
 * Java.lang.reflect.Proxy负责创建代理类
 * InvocationHandler负责处理执行逻辑
 */
public class MyProxy implements InvocationHandler {
    Object target;
    public MyProxy(Class clazz){
        try {
            this.target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param proxy 指代我们的代理对象，也是所代理的那个真实对象
     * @param method 指代的是我们所要调用真实对象的某个方法的Method对象
     * @param args 指代的是调用真实对象某个方法时接受的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy:"+proxy.getClass().getName());
        System.out.println("method:"+method.getName());
        System.out.println("args:"+args);
        Object res = method.invoke(target,args);
        return res;
    }
}
