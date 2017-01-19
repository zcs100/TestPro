package com.netty.beat.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by szc on 16/12/26.
 */
public class BeatCheckServer  {

    private static Logger logger = Logger.getLogger(BeatCheckServer.class);

    public BeatCheckServer() {
        start(8009);
    }

    public void start(int port) {
        logger.info("server init begin ..............");
        EventLoopGroup worker = new NioEventLoopGroup();
        EventLoopGroup boss = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try{
            serverBootstrap.group(boss,worker)
                    .localAddress(port)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new BeatCheckServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("server init finish ..............");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //启动服务
        BeatCheckServer server = new BeatCheckServer();

        /*logger.info("begin wait for...,the current time is "+new Date());
        Thread.sleep(1000);
        logger.info("finish wait for...,the current time is "+new Date());*/
        logger.info("---------------------------------");

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        while (true){
            TimeUnit.SECONDS.sleep(3);
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
            }
        }

    }
}
