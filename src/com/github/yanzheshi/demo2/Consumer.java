package com.github.yanzheshi.demo2;

/**
 * 消费者类
 * 消费过程:
 * 检查仓库是否空,当仓库空的时候,线程等待(同步),
 * 直到仓库不空才进行消费(同一时刻只能一个消费者消费)
 * Created by shiyanzhe on 2016/11/21.
 */
public class Consumer extends Thread{
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

        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //同一时刻只能有一件商品被消费
            Product product = storeHouse.remove();

            if (product != null) {
                System.out.println(name + "消费了编号为'" + product.getId() + "'的产品");
                System.out.println("仓库剩余: " + storeHouse.getCount());
            } else {
                System.out.println("没有可消费的产品!");
            }

    }

    @Override
    public void run() {
        while (true) {

            synchronized (storeHouse) {
                while (storeHouse.isEmpty()) {
                    try {
                        storeHouse.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                consume();
                storeHouse.notifyAll();
            }
        }
    }
}
