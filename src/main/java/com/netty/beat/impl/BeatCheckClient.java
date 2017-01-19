package com.netty.beat.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/26.
 */
public class BeatCheckClient {

    private Logger logger = Logger.getLogger(BeatCheckClient.class);

    public void start(String host,int port){
        logger.info("client init begin ..............");
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host,port)
                    //.option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new BeatCheckClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            logger.info("client init finish ..............");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BeatCheckClient checkClient = new BeatCheckClient();
        checkClient.start("127.0.0.1", 8009);


    }
}
