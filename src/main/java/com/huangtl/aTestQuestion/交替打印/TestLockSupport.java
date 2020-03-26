package com.huangtl.aTestQuestion.交替打印;

import java.util.concurrent.locks.LockSupport;

/**
 * 思路：两个现场执行时每次打印完将另外一个线程解锁、然后锁住自身等待对方解锁自己
 */
public class TestLockSupport {

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        String[] arr1 = {"a","b","c","d","e"};
        String[] arr2 = {"1","2","3","4","5"};

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String s : arr1) {
                    System.out.print(s);
                    LockSupport.unpark(t2);
                    LockSupport.park(this);
                }
                LockSupport.unpark(t2);//释放t2最后一次锁
            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park(this);
                for (String s : arr2) {
                    System.out.print(s);
                    LockSupport.unpark(t1);
                    LockSupport.park(this);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
