package com.rpc.demo;

/**
 * Created by szc on 17/2/27.
 */
public class RpcConsumer {

    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);

        String hello = service.hello("World");
        System.out.println(hello);
        Thread.sleep(1000);

    }

}
