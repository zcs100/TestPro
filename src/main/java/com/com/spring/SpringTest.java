package com.com.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by szc on 18/1/12.
 */

public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = ctx.getBean(MyService.class);
        myService.print();

        /*AppConfig appConfig = new AppConfig();
        appConfig.myService().print();*/
    }
}
