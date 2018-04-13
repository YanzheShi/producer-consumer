package com.github.yanzheshi.test;

/**
 * Created by shiyanzhe on 2016/11/22.
 */
public class WaitNotifyTest {
    public static void main(String[] args) {
        Object shareObject = new Object();

        MyThread3 thread3 = new MyThread3();
        thread3.setName("t3");
        thread3.setShareObjcet(shareObject);

        MyThread4 thread4 = new MyThread4();
        thread4.setName("t4");
        thread4.setShareObjcet(shareObject);

        thread3.start();
        thread4.start();
    }
}

class MyThread3 extends Thread {

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run() {
        //3. 使用所有线程共享的对象作为锁对象
        synchronized (shareObjcet) {
            for (int i = 0; i < 20; i++) {

                if (i == 10) {
                    try {
                        System.out.println("wait");
                        shareObjcet.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(getName() + ": " + i);
            }
        }
    }
}

class MyThread4 extends Thread {

    //关联的对象
    Object shareObjcet;

    public void setShareObjcet(Object shareObjcet) {
        this.shareObjcet = shareObjcet;
    }

    @Override
    public void run() {
        //3. 使用所有线程共享的对象作为锁对象
        synchronized (shareObjcet) {
            for (int i = 0; i < 20; i++) {

                if (i == 10) {
                    System.out.println("notify");
                    shareObjcet.notify();
                }

                System.out.println(getName() + ": " + i);
            }
        }
    }
}