package com.github.yanzheshi.test;

/**
 * synchronize关键字参数测试类
 *
 * synchronized代码块的测试:
 * synchronized关键字参数必须是多个线程共享的变量,才能达到同步目的
 * 例如:
 * 1. 如果用了类里面的一个属性object作为锁,由于这个属性
 * 每一个实例化对象都有一份,所以运行发现3个线程都没有同步
 * 2. 如果用了类对象的class对象或者是类的静态属性作为锁,
 * 由于这个对象是类共享的,所以同一个MyThread1类的实例能实现同步
 * 但与其他类仍不能实现同步
 * 3. 如果使用了关联的共享对象作为锁对象,由于这个对象所有线程共享,
 * 所以所有类都实现了同步
 *
 * synchronized方法体的测试
 * 4. 在方法定义时加上synchronize,并没有导致t4,和t5的同步
 * 5. 在其他类里面组合t4, 调用t4的synchronized方法, 同步执行
 * 说明synchronized方法的锁对象为该类的实例化对象, 相当于
 * synchronized(this)
 *
 * Created by shiyanzhe on 2016/11/22.
 */
public class SynchronizedTest {
    public static void main(String[] args) {

        //synchronized代码块的测试:
/*        System.out.println("synchronized代码块的测试:");
        Object shareObject = new Object();

        MyThread1 thread1 = new MyThread1();
        thread1.setName("t1");
        thread1.setShareObjcet(shareObject);

        MyThread1 thread2 = new MyThread1();
        thread2.setName("t2");
        thread2.setShareObjcet(shareObject);

        MyThread2 thread3 = new MyThread2();
        thread3.setName("t3");
        thread3.setShareObjcet(shareObject);

        thread1.start();
        thread2.start();
        thread3.start();*/


        //synchronized方法体的测试

        System.out.println("synchronized方法体的测试:");

        MyThread44 myThread4 = new MyThread44();
        myThread4.setName("t4");
        MyThread44 myThread5 = new MyThread44();
        myThread5.setName("t5");

//        myThread4.start();
//        myThread5.start();

/*        MyThread55 myThread6 = new MyThread55();
        myThread6.setName("t6");
        myThread6.setMyThread44(myThread4);
        MyThread55 myThread7 = new MyThread55();
        myThread7.setName("t7");
//        myThread7.setMyThread44(myThread4);
        myThread7.setMyThread44(myThread5);


        myThread6.start();
        myThread7.start();*/



    }
}

class MyThread1 extends Thread{
    Object object = new Object();
    static Object staticObject = new Object();

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run() {
        //1. 使用非共享的对象作为锁对象
//        synchronized (object){
        //2. 使用类共享的对象作为锁对象
        synchronized (getClass()) {
        //synchronized (staticObject){
            //3. 使用所有线程共享的对象作为锁对象
//            synchronized (shareObjcet){
            for (int i = 0; i < 20; i++) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + ": " + i);
            }
        }
    }
}

class MyThread2 extends Thread{
    Object object = new Object();
    static Object staticObject = new Object();

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run(){

        //1. 使用非共享的对象作为锁对象
        //synchronized (object){
        //2. 使用类共享的对象作为锁对象
        //synchronized (getClass()) {
        //synchronized (staticObject){
            //3. 使用所有线程共享的对象作为锁对象
            //synchronized (shareObjcet){
            for (int i = 0; i < 50; i++) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + ": " + i);
            }
        //}
    }
}

class MyThread44 extends Thread{

    public synchronized void method1(String name){

        for (int i = 0; i < 20; i++) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": method1: " + i);
        }
    }

    public synchronized void method2(String name){

        for (int i = 0; i < 20; i++) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": method2: " + i);
        }
    }
    @Override
    public void run() {
        method1(getName());
    }
}

class MyThread55 extends Thread{

    public MyThread44 myThread44;

    public void setMyThread44(MyThread44 myThread44) {
        this.myThread44 = myThread44;
    }

    @Override
    public void run() {
        myThread44.method1(getName());
        myThread44.method2(getName());
    }
}


