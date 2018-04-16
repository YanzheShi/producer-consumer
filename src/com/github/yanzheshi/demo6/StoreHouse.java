package com.github.yanzheshi.demo6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库
 * 底层是一个循环阻塞队列
 * 通过放弃1个空间来区分队首和队尾
 * Created by shiyanzhe on 2016/11/21.
 */
public class StoreHouse {
    //仓库的容量
    private int capacity;
    //货品数组
    private Product[] store;
    //队头位置
    private int front;
    //队尾位置
    private int rear;

    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    public StoreHouse() {
        //默认设置为16
        capacity = 16+1;
        store = new Product[capacity];
        front = 0;
        rear = 0;
    }

    public StoreHouse(int cap) {
        this.capacity = cap + 1;
        store = new Product[capacity];
        front = 0;
        rear = 0;
    }

    /**
     * 判断仓库是否为空
     * @return 是否为空
     */
    public boolean isEmpty() {
        return front == rear;
    }

    /**
     * 判断仓库是否已满
     * @return 是否已满
     */
    public boolean isFull(){
        return front == (rear + 1) % capacity;
    }

    /**
     * 为仓库添加新的货物
     * 如果仓库已满,则阻塞线程
     * @param newProduct 先的货物
     * @return 是否添加成功
     */
    public void add (Product newProduct) throws InterruptedException{
        lock.lock();

        try {
            while (isFull()) {
                notFull.await();
            }
            store[rear] = newProduct;
            rear = (rear + 1) % capacity;

            System.out.println("生产了编号为'" + newProduct.getId() + "'的商品");
            System.out.println("仓库剩余: " + getCount());

            //唤醒所有消费者
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从仓库取出货品
     * @return 返回取出的货品,当仓库为空时,阻塞线程,
     * 直到仓库不为空
     */
    public Product remove() throws InterruptedException{
        lock.lock();

        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            Product product = store[front];
            //避免内存泄漏
            store[front] = null;
            front = (front + 1) % capacity;

            System.out.println("消费了编号为'" + product.getId() + "'的商品");
            System.out.println("仓库剩余: " + getCount());

            //唤醒所有生产者
            notFull.signalAll();
            return product;
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @return
     */
    public synchronized int getCount(){
        return (rear - front + capacity) % capacity;
    }
}
