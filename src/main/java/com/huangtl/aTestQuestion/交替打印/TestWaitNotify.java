package com.huangtl.aTestQuestion.交替打印;

/**
 * 思路
 * 1.线程2先锁住自己，让线程1先打印
 * 2.线程1打印完释放线程2，然后锁住自己，线程2同样操作
 * 3.最后一次线程2打印完释放线程1锁住自己时，在线程1后面释放线程2（否则线程2一直锁着）
 *
 * 注意点：notify()和wait()方法要写在同步块synchronized中
 */
public class TestWaitNotify {

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
                    synchronized (t2){
                        t2.notify();
                    }
                    synchronized (t1){
                        try {
                            t1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                synchronized (t2){
                    t2.notify();
                }
            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (t2) {
                        t2.wait();
                    }
                    for (String s : arr2) {
                        System.out.print(s);
                        synchronized (t1) {
                            t1.notify();
                        }
                        synchronized (t2) {
                            t2.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
