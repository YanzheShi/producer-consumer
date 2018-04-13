package com.github.yanzheshi.demo2;

import java.util.Scanner;

/**
 * 生产者与消费者模拟
 * 与demo1相比, 去掉了produce和consume方法中的synchronized,
 * 因为这两个方法本来就是在同步块上执行的的,所以方法里面不用再去同步
 * Created by shiyanzhe on 2016/11/21.
 */
public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //公用一个仓库
        int proNum,  conNum, storeSize;

        System.out.println("仓库容量");
        storeSize = scanner.nextInt();
        System.out.println("生产者数量");
        proNum = scanner.nextInt();
        System.out.println("消费者数量");
        conNum = scanner.nextInt();

        scanner.close();

        StoreHouse storeHouse = new StoreHouse(storeSize);

        for (int i = 0; i < proNum; i++) {
            Producer producer = new Producer("p" + i, storeHouse);
            producer.start();
        }

        for (int i = 0; i < conNum; i++) {
            Consumer consumer = new Consumer("c" + i, storeHouse);
            consumer.start();
        }

    }
}
