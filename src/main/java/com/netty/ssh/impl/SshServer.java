package com.netty.ssh.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by szc on 16/12/14.
 */
public class SshServer {

    private static Logger logger = Logger.getLogger(SshServer.class);

    private final int port;

    public SshServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        if(args.length!=1){
            logger.error("参数错误");
        }
        int port = Integer.valueOf(args[0]);
        SshServer echoServer = new SshServer(port);
        echoServer.start();
    }

    public void start() throws Exception{
        logger.info("SshServer init........ ");
        final SshServerHandler sshServerHandler = new SshServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(sshServerHandler);
                        }
                    });
            ChannelFuture channelFuture = server.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("EchoClient is destroyed........");
            eventLoopGroup.shutdownGracefully().sync();
        }

    }
}
