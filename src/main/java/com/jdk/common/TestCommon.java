package com.jdk.common;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by szc on 17/2/23.
 */
public class TestCommon {

    private Logger logger = Logger.getLogger(TestCommon.class);

    /**
     * 测试线程设置为守护线程后,执行完成是否程序自动退出
     */
    @Test
    public void testDaemon() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true){
                    System.out.println("测试Daemon线程释放问题");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(2000);
    }

    /**
     * 测试线程池中的线程设置成Daemon   执行完成后是否程序自动退出
     */
    @Test
    public void testPoolDaemon(){
        ExecutorService executorService = Executors.newSingleThreadExecutor(
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            }
        );
        Thread thread = new Thread(
            new Runnable() {
                public void run() {
                    System.out.println("测试Daemon线程释放问题");
                }
            }
        );
        //thread.setDaemon(false);
        thread.setDaemon(true);
        executorService.execute(thread);
        executorService.shutdown();
    }

    /**
     * 测试系统常用变量
     */
    @Test
    public void systemPropertyTest(){
        //   /Users/szc/TestWorkPlace/TestPro
        logger.info(System.getProperty("user.dir"));
    }

    /**
     * 测试 String.intern 方法在 jdk1.6 1.7中的区别
     * http://vaporz.blog.51cto.com/3142258/600904
     * http://www.linmuxi.com/2016/03/02/string-intern-jdk6-jdk7-01/
     */
    @Test
    public void testPermString(){
        String str1 = new StringBuilder("计算机").append("技术").toString();
        logger.info("======"+str1.intern()==str1+"===========");

        String str2 = new StringBuilder("ja").append("va").toString();
        logger.info("======"+str2.intern()==str2+"===========");

        String str3 = new String("1") + new String("1");
        logger.info("======"+str3.intern()==str3+"===========");
    }

    /**
     * 测试枚举类型的比较
     */
    @Test
    public void testEnum(){
        logger.info("==============="+(DateEnum.MON == DateEnum.MON));
        logger.info("==============="+(DateEnum.MON.equals(DateEnum.MON)));
    }

    @Test
    public void testThreadJoin(){
        logger.info("begin execute main method");
        try {
            /*Thread t = new Thread(new ThreadTest());
            t.start();
            t.join(1000);*/

            test();
            logger.error("直接跳过join方法了,哈哈哈");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        logger.info("end execute main method");
    }

    private class ThreadTest implements Runnable{
        public void run() {
            while (true){
                System.out.println("current data is ");
            }
        }
    }

    private void test() throws InterruptedException{
        Thread.currentThread().join(10);
        while (true){

        }
    }

    @Test
    public void testFuture(){
        final ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(100 * 5);
                return "线程执行完成.";
            }
        };

        try {
            Future<String> future = exec.submit(call);
            String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        }
        // 关闭线程池
        exec.shutdown();
    }

    @Test
    public void testInterrupt() throws InterruptedException{
        Thread t = new Thread(){
            @Override
            public void run() {
                while(true){
                    Thread.yield();
                   // System.out.println("current data is ");
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }

    /**
     * 测试同一个对象的可重入锁
     */
    @Test
    public void testSychronied(){
        Object o = new Object();
        synchronized (o){
            System.out.println("enter into lock 1");
            synchronized (o){
                System.out.println("enter into lock 2");
            }
        }
    }

    /**
     * 测试类加载器
     */
    @Test
    public void testClassLoader(){
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
    }
}
