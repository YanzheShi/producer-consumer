package com.github.yanzheshi.demo4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 仓库
 * 封装一个阻塞队列
 * Created by shiyanzhe on 2016/11/21.
 */
public class StoreHouse {
    //仓库的容量
    private int capacity;
    //货品数组
    private BlockingQueue<Product> blockingQueue;


    public StoreHouse() {
        //默认设置为16
        capacity = 16;
        blockingQueue = new ArrayBlockingQueue<>(capacity);
    }

    public StoreHouse(int cap) {
        this.capacity = cap;
        blockingQueue = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * 判断仓库是否为空
     * @return 是否为空
     */
    public synchronized boolean isEmpty() {
        return blockingQueue.isEmpty();
    }

    /**
     * 判断仓库是否已满
     * @return 是否已满
     */
    public synchronized boolean isFull(){
        return blockingQueue.size() == capacity;
    }

    /**
     * 为仓库添加新的货物
     * 如果仓库已满,则返回false
     * @param newProduct 先的货物
     * @return 是否添加成功
     */
    public  boolean add(Product newProduct){
        try {
            blockingQueue.put(newProduct);

            return  true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;


    }

    /**
     * 从仓库取出货品
     * @return 返回取出的货品,当仓库为空时,返回null
     */
    public  Product remove() {
        Product newProduct = null;
        try {
            newProduct = blockingQueue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newProduct;
    }

    /**
     *
     * @return
     */
    public synchronized int getCount(){
        return blockingQueue.size();
    }
}
