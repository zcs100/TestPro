package com.algorithm.demo;

/**
 * Created by szc on 17/9/28.
 */
public class ConsumerAndProducer {

    private static class Consumer implements Runnable{
        private ArrayBlockQueueDemo arrayBlockQueueDemo;

        public Consumer(ArrayBlockQueueDemo arrayBlockQueueDemo) {
            this.arrayBlockQueueDemo = arrayBlockQueueDemo;
        }

        public void run() {
            try{
                System.out.println("我正在消费"+arrayBlockQueueDemo.take());
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static class Producer implements Runnable{
        private ArrayBlockQueueDemo arrayBlockQueueDemo;

        public Producer(ArrayBlockQueueDemo arrayBlockQueueDemo) {
            this.arrayBlockQueueDemo = arrayBlockQueueDemo;
        }
        public void run() {
            try{
                arrayBlockQueueDemo.put("1");
                System.out.println("生产中.....");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException{
        ArrayBlockQueueDemo arrayBlockQueueDemo = new ArrayBlockQueueDemo();
        new Thread(new Consumer(arrayBlockQueueDemo)).start();
        new Thread(new Producer(arrayBlockQueueDemo)).start();
    }
}
