package com.github.yanzheshi.demo6;

/**
 * 消费者类
 * 消费过程:
 * 检查仓库是否空,当仓库空的时候,线程等待(同步),
 * 直到仓库不空才进行消费(同一时刻只能一个消费者消费)
 * Created by shiyanzhe on 2016/11/21.
 */
public class Consumer extends Thread {
    //消费者名称
    private String name;
    //对应的仓库
    private StoreHouse storeHouse;

    public Consumer() {

    }

    public Consumer(String name, StoreHouse storeHouse) {
        this.name = name;
        this.storeHouse = storeHouse;
    }

    /**
     * 消费商品方法
     */
    public void consume() {

        //同一时刻只能有一件商品被消费

        try {
            storeHouse.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            consume();
        }
    }
}
