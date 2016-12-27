package com.netty.echo.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by szc on 16/12/6.
 */
public class EchoServer  {

    private static Logger logger = Logger.getLogger(EchoServer.class);

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        if(args.length!=1){
            logger.error("参数错误");
        }
        int port = Integer.valueOf(args[0]);
        EchoServer echoServer = new EchoServer(port);
        echoServer.start();
    }

    public void start() throws Exception{
        logger.info("EchoServer init........ ");
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(echoServerHandler);
                        }
                    });
            ChannelFuture channelFuture = server.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("EchoServer is destroyed........");
            eventLoopGroup.shutdownGracefully().sync();
        }

    }
}
