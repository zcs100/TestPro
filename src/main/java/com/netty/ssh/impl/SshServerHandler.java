package com.netty.ssh.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/14.
 */
@ChannelHandler.Sharable
public class SshServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(SshServer.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("SshServerHandler.channelInactive ........");
        ctx.writeAndFlush("welcome to .....");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("SshServerHandler.exceptionCaught ........");
        cause.printStackTrace();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("SshServerHandler.channelRegistered ........");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("SshServerHandler.channelActive ........");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        logger.info("SshServerHandler.channelRead ........" + buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("SshServerHandler.channelReadComplete ........");

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello world".getBytes()));
    }
}
