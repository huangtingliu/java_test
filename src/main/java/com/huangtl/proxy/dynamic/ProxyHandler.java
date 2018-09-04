package com.huangtl.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 参考网上的封装类
 * @param <T>
 */
public abstract class ProxyHandler<T> implements InvocationHandler {

    private T target;

    public ProxyHandler(T target){
        this.target = target;
    }

    /**
     * 获取代理类对象
     * @return
     */
    public T getProxy(){
        Class<T> clazz = (Class<T>) target.getClass();
        if(clazz.getInterfaces() != null){
            return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), clazz.getInterfaces(), this);
        }
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object res = null;

        try {
            before(method,args);

            res = method.invoke(target, args);

            after(method,args);
        } catch (Exception e) {
            e.printStackTrace();
            onException(e);
        }
        return res;
    }

    /**
     * 代理前处理
     * @param method
     * @param args
     */
    public abstract void before(Method method,Object[] args);

    /**
     * 代理后处理
     * @param method
     * @param args
     */
    public abstract void after(Method method,Object[] args);

    /**
     * 异常发生处理
     * @param e
     */
    public abstract void onException(Exception e);


}
