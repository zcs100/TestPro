package com.face.demo;

/**
 * Created by szc on 17/3/30.
 * 多线程输出10次ABC
 *
 * 程序存在问题  : 最后会有线程一直在wait,导致线程阻塞
 * ///默认最后一次执行B/C线程会一直等待,暂时解决办法可以在代码40行,进行对象notify,使线程退出等待
 */
public class ThreadSystemOutABC {

    static class ThreadPrint implements Runnable{

        private String printContent;
        private Object preObj;
        private Object selfObj;

        private ThreadPrint(String printContent,Object preObj,Object selfObj){
            this.printContent = printContent;
            this.preObj = preObj;
            this.selfObj = selfObj;
        }
        public void run() {
            int count = 10;
            for(int i = 0; i<10; i++){
                synchronized (preObj){
                    synchronized (selfObj){
                        System.out.print(printContent);
                        count--;
                        selfObj.notify();
                    }
                    try {
                        preObj.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
            synchronized (selfObj){
                selfObj.notify();
            }
            System.out.print(printContent +" print finished!!!");
        }


    }

    public static void main(String[] args) throws InterruptedException{
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Thread pa = new Thread(new ThreadPrint("A",c,a));
        Thread pb = new Thread(new ThreadPrint("B",a,b));
        Thread pc = new Thread(new ThreadPrint("C",b,c));
        pa.start();
        Thread.sleep(100);
        pb.start();
        Thread.sleep(100);
        pc.start();
        Thread.sleep(100);
    }

}
