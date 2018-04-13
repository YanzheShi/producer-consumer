package com.github.yanzheshi.demo4;

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

//        try {
//            System.out.println(name  + "sleep");
//            sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        /**
         * 其实不用使用synchronized, 也能正确实现生产和消费过程, 但为了能在生产和消费
         * 完毕后能立即打印信息, 所以尝试使用同步块
         *
         * 1. 如果生产者与消费者都使用synchronized (storeHouse)
         * 会导致死锁, 因为生产者生产时如果被阻塞, 即storeHouse.add()方法被阻塞了,
         * 但同步块没有结束,仍然持有者storeHouse的锁, 而消费者的锁也是storeHouse
         * 所以导致死锁
         * 2. 所有生产者共用一个锁对象, 消费者共用一个锁对象. 但add方法执行完
         * 后println方法前,可能被消费者打断, 还是不能在生产和消费后立即打印信息
         *
         */
        //同一时刻只能有一件商品被消费
        //synchronized (getClass()) {
            Product product = storeHouse.remove();

            if (product != null) {
                System.out.println(name + "消费了编号为'" + product.getId() + "'的产品");
                System.out.println("仓库剩余: " + storeHouse.getCount());
            } else {
                System.out.println("没有可消费的产品!");
            }

        //}
    }

    @Override
    public void run() {
        while (true) {
            consume();
        }
    }

}
