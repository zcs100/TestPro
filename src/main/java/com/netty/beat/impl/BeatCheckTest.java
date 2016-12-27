package com.netty.beat.impl;

import io.netty.channel.socket.SocketChannel;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by szc on 16/12/27.
 */
public class BeatCheckTest {

    public static void main(String[] args) throws InterruptedException{
        //启动服务
        BeatCheckServer server = new BeatCheckServer();
        server.start(8009);

        BeatCheckClient checkClient = new BeatCheckClient();
        checkClient.start("127.0.0.1",8009);

        Thread.sleep(1000);

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Iterator iterator = connectionPool.

        Collection it = connectionPool.values();
        for(Object channelObj : it){
            SocketChannel channel = (SocketChannel)channelObj;

            channel.writeAndFlush();
        }
    }

}
