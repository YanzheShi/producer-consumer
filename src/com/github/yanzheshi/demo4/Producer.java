package com.github.yanzheshi.demo4;

/**
 * 生产者类
 * 生产过程:
 * 检查仓库是否满,当仓库满的时候,线程等待(同步),
 * 生产(同一时刻只允许一个生产者生产 同步)
 * <p>
 * Created by shiyanzhe on 2016/11/21.
 */
public class Producer extends Thread {
    //生产者名称
    private String name;
    //对应的仓库
    private StoreHouse storeHouse;
    //商品序号，自增
    private static int number = 0;

    public Producer() {
    }

    public Producer(String name, StoreHouse storeHouse) {
        this.name = name;
        this.storeHouse = storeHouse;
    }

    public void setStoreHouse(StoreHouse storeHouse) {
        this.storeHouse = storeHouse;
    }


    /**
     * 生产商品方法
     * 商品编号为生产者名字_商品序号
     */
    public void produce() {

        Product newProduct = new Product();

        String id = name + "_" + number;
        newProduct.setId(id);

//        try {
//            System.out.println(name + "sleep");
//            sleep(100);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //同一时刻只能有一件商品入库

        /**
         * 其实不用使用synchronized, 也能正确实现生产和消费过程, 但为了能在生产和消费
         * 完毕后能立即打印信息, 所以尝试使用同步块
         *
         * 1. 如果生产者与消费者都使用synchronized (storeHouse)
         * 会导致死锁, 因为生产者生产时如果被阻塞, 即storeHouse.add()方法被阻塞了,
         * 但同步块没有结束,仍然持有者storeHouse的锁, 而消费者的锁也是storeHouse
         * 所以导致死锁
         * 2. 所有生产者共用一个锁对象, 消费者共用一个锁对象. 但add方法执行完
         * 后println方法前,可能被消费者打断. 还是不能在生产和消费后立即打印信息
         *
         */

        //synchronized (getClass()) {
            if (storeHouse.add(newProduct) == true) {
                System.out.println(name + "生产了编号为'" + id + "'的商品");
                System.out.println("仓库剩余: " + storeHouse.getCount());
                number++;

                if (number == 100) {
                    System.exit(0);
                }
            } else {
                System.out.println("仓库已满!");
            }
        //}
    }

    /**
     * 生产线程
     * 需要检测仓库,当仓库满的时候,线程等待
     */
    @Override
    public void run() {
        while (true) {
            produce();
        }
    }
}
