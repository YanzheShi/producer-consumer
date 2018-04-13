package com.github.yanzheshi.test;

/**
 * sleep方法测试类
 * sleep不是释放对象锁
 * 1. 当锁不是共享对象的话,sleep会导致线程切换(切换到其他监视器的线程)
 * 2. 当锁是共享对象的话,sleep结束后继续执行该线程
 * Created by shiyanzhe on 2016/11/22.
 */
public class SleepTest {
    public static void main(String[] args) {
        Object shareObject = new Object();

        MyThread5 thread5 = new MyThread5();
        thread5.setName("t5");
        thread5.setShareObjcet(shareObject);

        MyThread6 thread6 = new MyThread6();
        thread6.setName("t6");
        thread6.setShareObjcet(shareObject);

        thread5.start();
        thread6.start();
    }
}

class MyThread5 extends Thread {
    Object object = new Object();
    static Object staticObject = new Object();

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run() {
        //1. 随意使用非共享的对象作为锁对象
        //synchronized (object){
        //2. 使用类共享的对象作为锁对象
        //synchronized (getClass()) {
        //synchronized (staticObject){
        //3. 使用所有线程共享的对象作为锁对象

        synchronized (shareObjcet) {
            for (int i = 0; i < 20; i++) {
                try {
                    System.out.println("t5 sleep");
                    sleep(1000);
                    System.out.println("t5 wake up");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + ": " + i);
            }
        }
    }
}

class MyThread6 extends Thread {
    Object object = new Object();
    static Object staticObject = new Object();

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run() {

        //1. 随意使用非共享的对象作为锁对象
        //synchronized (object){
        //2. 使用类共享的对象作为锁对象
        //synchronized (getClass()) {
        //synchronized (staticObject) {
        //3. 使用所有线程共享的对象作为锁对象
        synchronized (shareObjcet) {
            for (int i = 0; i < 50; i++) {
                try {
                    System.out.println("t6 sleep");
                    sleep(1000);
                    System.out.println("t6 wake up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getName() + ": " + i);
            }
        }
    }
}
