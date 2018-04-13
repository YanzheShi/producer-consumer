package com.github.yanzheshi.demo1;

/**
 * 仓库
 * 底层是一个循环队列
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
    public synchronized boolean isEmpty() {
        return front == rear;
    }

    /**
     * 判断仓库是否已满
     * @return 是否已满
     */
    public synchronized boolean isFull(){
        return front == (rear + 1) % capacity;
    }

    /**
     * 为仓库添加新的货物
     * 如果仓库已满,则返回false
     * @param newProduct 先的货物
     * @return 是否添加成功
     */
    public synchronized boolean add(Product newProduct){
        if (isFull()) {
            return false;
        }
        store[rear] = newProduct;
        rear = (rear + 1) % capacity;
        return true;
    }

    /**
     * 从仓库取出货品
     * @return 返回取出的货品,当仓库为空时,返回null
     */
    public synchronized Product remove() {
        if (isEmpty()) {
            return null;
        }
        Product product = store[front];
        //避免内存泄漏
        store[front] = null;
        front = (front + 1) % capacity;
        return product;
    }

    /**
     *
     * @return
     */
    public synchronized int getCount(){
        return (rear - front + capacity) % capacity;
    }
}
