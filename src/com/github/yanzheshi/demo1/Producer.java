package com.github.yanzheshi.demo1;

/**
 * 生产者类
 * 生产过程:
 * 检查仓库是否满,当仓库满的时候,线程等待(同步),
 * 生产(同一时刻只允许一个生产者生产 同步)
 *
 * Created by shiyanzhe on 2016/11/21.
 */
public class Producer extends Thread{
    //生产者名称
    private String name;
    //对应的仓库
    private StoreHouse storeHouse;
    //商品序号，自增
    private static int number = 0;

    public Producer(){
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
    public void produce(){

        Product newProduct = new Product();

        String id = name + "_" + number;
        newProduct.setId(id);

        //模拟生产过程,让线程等待一下在继续执行,并没有放弃锁
        //同时方便观察线程切换
        try {
            System.out.println(name  + "sleep");
            sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //同一时刻只能有一件商品入库
        synchronized (storeHouse) {
            if (storeHouse.add(newProduct) == true) {
                System.out.println(name + "生产了编号为'" + id + "'的商品");
                System.out.println("仓库剩余: " + storeHouse.getCount());
                number++;
                //生产100个商品，让程序自动停止
                if (number == 100) {
                    System.exit(0);
                }
            } else {
                System.out.println("仓库已满!");
            }
        }

    }

    /**
     * 生产线程
     * 需要检测仓库,当仓库满的时候,线程等待
     */
    @Override
    public void run() {
        while (true) {

            synchronized (storeHouse) {
                while (storeHouse.isFull()) {
                    try {
                        storeHouse.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                produce();
                storeHouse.notifyAll();
            }

        }
    }

}
