package com.rpc.demo;

/**
 * Created by szc on 17/2/27.
 */
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello " + name;
    }
}
