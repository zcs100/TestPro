package com.jdk.dynamicProxy;

import static java.lang.reflect.Proxy.getProxyClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by szc on 17/7/31.
 */
public class JDKProxyTest {
    public static void main(String[]args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //这里有两种写法，我们采用略微复杂的一种写法，这样更有助于大家理解。
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Class<?> proxyClass= getProxyClass(JDKProxyTest.class.getClassLoader(),HelloWord.class);
        final Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);
        final InvocationHandler ih = new MyInvocationHandler(new HelloWordImpl());
        HelloWord helloWorld= (HelloWord)cons.newInstance(ih);
        helloWorld.sayHello();

        //下面是更简单的一种写法，本质上和上面是一样的
        /*
        HelloWorld helloWorld=(HelloWord)Proxy.
                 newProxyInstance(JDKProxyTest.class.getClassLoader(),
                        new Class<?>[]{HelloWorld.class},
                        new MyInvocationHandler(new HelloWordImpl()));
        helloWorld.sayHello();
        */
    }

}
