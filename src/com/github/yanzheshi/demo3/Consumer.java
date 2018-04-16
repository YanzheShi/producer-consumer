package com.github.yanzheshi.demo3;

/**
 * 消费者类
 * 消费过程:
 * 轮询判断， 当仓库不空就消费
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

        try {
            System.out.println(name + "sleep");
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

    /**
     * 消费线程
     * 当仓库不空就消费
     */
    @Override
    public void run() {
        while (true) {
            synchronized (storeHouse) {
                if (!storeHouse.isEmpty()) {
                    consume();
                }
            }
        }
    }

}
