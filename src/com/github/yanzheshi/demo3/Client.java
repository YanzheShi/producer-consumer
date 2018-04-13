package com.github.yanzheshi.demo3;

import java.util.Scanner;

/**
 * 生产者消费者模拟
 * 与demo2相比, 换了一种同步形式--没有使用wait和notifyAll
 * 而是利用简单的判断去实现:
 * 当仓库不满就生产
 * 当仓库不空就消费
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
