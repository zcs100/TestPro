package com.netty.ssh.impl;

import com.netty.echo.impl.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/14.
 */
public class SshClient {

    private static Logger logger = Logger.getLogger(SshClient.class);

    private final String host;

    private final int port;

    public SshClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        logger.info("SshClient init .......");
        final SshClientHandler clientHandler = new SshClientHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host,port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(clientHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("SshClient is destroyed........");
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
        if(args.length!=2){
            logger.info("参数错误");
        }
        int port = Integer.valueOf(args[1]);
        new SshClient(args[0],port).start();

    }
}
