package com.netty.beat.impl;

import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by szc on 16/12/27.
 */
public class BeatCheckTest {

    private static Logger logger = Logger.getLogger(BeatCheckTest.class);

    public static void main(String[] args) throws InterruptedException{
        //启动服务
        BeatCheckServer server = new BeatCheckServer();
        server.start(8009);

        /*logger.info("begin wait for...,the current time is "+new Date());
        Thread.sleep(1000);
        logger.info("finish wait for...,the current time is "+new Date());

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Iterator iterator = connectionPool.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            SocketChannel channel = (SocketChannel)entry.getValue();
            String channelId = (String)entry.getKey();
            RequestObj requestObj = new RequestObj();
            requestObj.setChannelId(channelId);
            requestObj.setMsg("ok");
            requestObj.setType(Type.BEAT_CHECK.toString());
            channel.writeAndFlush(requestObj);
        }*/
    }

}
