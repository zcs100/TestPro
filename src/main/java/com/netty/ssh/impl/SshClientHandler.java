package com.netty.ssh.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/14.
 */
public class SshClientHandler extends SimpleChannelInboundHandler{

    private Logger logger = Logger.getLogger(SshClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("SshClientHandler channelActive method .........");
        ctx.writeAndFlush(Unpooled.copiedBuffer("ssh root@47.95.116.101", CharsetUtil.UTF_8));
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("SshClientHandler channelRead0 method .........");
        logger.info(((ByteBuf)o).toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("SshClientHandler channelRead method .........");
        logger.info(((ByteBuf)msg).toString());
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = {};
        byteBuf.readBytes(bytes);
        System.out.println(new String(bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("SshClientHandler exceptionCaught method .........");
        cause.printStackTrace();
    }
}
