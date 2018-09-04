package com.huangtl.proxy.dynamic;

import java.lang.reflect.Method;

/**
 * 参考网上的封装类
 * @param <T>
 */
public class ProxyUtil<T> extends ProxyHandler<T> {

    public ProxyUtil(T target) {
        super(target);
    }

    @Override
    public void before(Method method, Object[] args) {
        System.out.println("before");
    }

    @Override
    public void after(Method method, Object[] args) {
        System.out.println("after");
    }

    @Override
    public void onException(Exception e) {
        System.out.println("Exception");
        e.printStackTrace();
    }
}
