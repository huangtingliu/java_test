package com.huangtl.designMode.singleton;

/**
 *  懒汉模式
 *  先声明一个实例变量，第一次调用的时候才会创建实例,但要保证线程安全
 */
public class Singleton2 {

    //1.私有化构造函数
    private Singleton2(){
        System.out.println("初始化Singleton2");
    }

    //2.声明实例变量
    private static Singleton2 instance;

    //被volatile修饰的成员变量可以确保多个线程都能够正确处理
    private volatile static Singleton2 instanceV;

    //3.提供外部调用实例(有问题)
    public static Singleton2 getInstance1(){
        if(instance==null){
            /**
             * 注意此过程需要一定时间，如果在此期间又有一个请求调用getInstance的话，
             * 因为还是instance还是为null，所以又会创建一次新对象，一般出现在多线程环境
             */
            instance = new Singleton2();
        }
        return instance;
    }

    /**
     * 4.提供外部调用实例(有问题)
     * 增加synchronized关键字
     * 虽然解决了线程安全问题，但是每次调用getInstance()时都需要进行线程锁定判断，
     * 在多线程高并发访问环境中，将会导致系统性能大大降低
     */
    public synchronized static Singleton2 getInstance2(){
        if(instance==null){
            instance = new Singleton2();
        }
        return instance;
    }

    /**
     * 5.提供外部调用实例(有问题)
     * 事实上，我们无须对整个getInstance()方法进行锁定，
     * 只需对其中的代码“instance = new Singleton2();”进行锁定即可
     */
    public  static Singleton2 getInstance3(){
        if(instance==null){
            synchronized(Singleton2.class){
                instance = new Singleton2();
            }
        }
        return instance;
    }

    /**
     * 6.提供外部调用实例(使用锁机制+双重检查）(不完美，影响性能)
     * 如上代码，假如在某一瞬间线程A和线程B都在调用getInstance()方法，
     * 此时instance对象为null值，均能通过instance == null的判断。
     * 由于实现了synchronized加锁机制，线程A进入synchronized锁定的代码中执行实例创建代码，线程B处于排队等待状态，
     * 必须等待线程A执行完毕后才可以进入synchronized锁定代码。
     * 但当A执行完毕时，线程B并不知道实例已经创建，将继续创建新的实例，导致产生多个单例对象，违背单例模式的设计思想，
     * 因此需要进行进一步改进，在synchronized中再进行一次(instance == null)判断，
     * 这种方式称为双重检查锁定(Double-Check Locking)
     *
     *
     * 需要注意的是，如果使用双重检查锁定来实现懒汉式单例类，需要在静态成员变量instance之前增加修饰符volatile，
     * 被volatile修饰的成员变量可以确保多个线程都能够正确处理，
     * 且该代码只能在JDK 1.5及以上版本中才能正确执行。
     * 由于volatile关键字会屏蔽Java虚拟机所做的一些代码优化，可能会导致系统运行效率降低，
     * 因此即使使用双重检查锁定来实现单例模式也不是一种完美的实现方式。
     */
    public  static Singleton2 getInstance(){
        if(instanceV==null){
            synchronized(Singleton2.class){
                if(instanceV==null) {
                    instanceV = new Singleton2();
                }
            }
        }
        return instanceV;
    }

}
