package com.algorithm.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by szc on 17/9/28.
 */
public class ArrayBlockQueueDemo<T> {

    ReentrantLock reentrantLock = new ReentrantLock();

    Condition conditionNotNull = reentrantLock.newCondition();

    Condition conditionNotFull = reentrantLock.newCondition();

    Object[] objects = null;

    int count ;

    public ArrayBlockQueueDemo() {
        objects = new Object[10];
    }

    public boolean put(T t) throws InterruptedException{
        ReentrantLock lock = this.reentrantLock;
        lock.lockInterruptibly();
        try {
            if (objects.length == count) {
                conditionNotNull.await();
            }
            objects[count] = t;
            ++count;
            conditionNotFull.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException{
        ReentrantLock lock = this.reentrantLock;
        lock.lockInterruptibly();
        try {
            if(count == 0){
                conditionNotFull.await();
            }
            --count;
            conditionNotNull.signal();
            return (T)objects[0];
        }finally {
            lock.unlock();
        }

    }


}
