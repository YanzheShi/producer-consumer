package com.github.yanzheshi.demo4;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 生产者消费者模拟
 * 前三个都是使用旧的同步方式来实现的。这种方式已经过时
 * 应该根据需求，使用新的并发框架来实现。
 * 新的并发框架能提供更细粒度的并发，更高效的实现。
 * 与demo3相比,使用JDK中的Executor和BlockingQueue实现
 * 仓库底层封装了一个BlockingQueue
 * 虽然能正确模拟, 但不能保证生产和消费后能立即打印对应信息
 * 即输出语句错乱
 * 粒度更细，只能到仓库级别上的同步，但是输出是由生产者
 * 与消费者完成的， 所以不能保证输出顺序正确。
 * 除非输出放在仓库类里面，如何demo5
 *
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
        Executor executor = Executors.newFixedThreadPool(conNum + proNum);

        for (int i = 0; i < proNum; i++) {
            Producer producer = new Producer("p" + i, storeHouse);
            executor.execute(producer);
        }

        for (int i = 0; i < conNum; i++) {
            Consumer consumer = new Consumer("c" + i, storeHouse);
            executor.execute(consumer);
        }

    }
}
