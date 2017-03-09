package com.jdk.common;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by szc on 17/2/23.
 */
public class TestCommon {

    private Logger logger = Logger.getLogger(TestCommon.class);

    /**
     * 测试线程设置为守护线程后,执行完成是否程序自动退出
     */
    @Test
    public void testDaemon(){
        Thread thread = new Thread(new Runnable() {

            public void run() {
                System.out.println("测试Daemon线程释放问题");
            }
        });
        thread.setDaemon(true);
        thread.start();
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
        /*logger.info("==============="+(DateEnum.MON == DateEnum.MON));
        logger.info("==============="+(DateEnum.MON.equals(DateEnum.MON)));*/
    }

}
