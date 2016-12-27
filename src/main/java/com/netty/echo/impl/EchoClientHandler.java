package com.netty.echo.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/6.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

    private Logger logger = Logger.getLogger(EchoClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("EchoClientHandler channelActive method .........");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("First netty", CharsetUtil.UTF_8));
        ctx.writeAndFlush("First netty");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("EchoClientHandler channelRead0 method .........");
        logger.info(((ByteBuf)o).toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("EchoClientHandler channelRead method .........");
        //logger.info(((ByteBuf)msg).toString());
        logger.info(msg.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("EchoClientHandler channelReadComplete method .........");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("EchoClientHandler exceptionCaught method .........");
        cause.printStackTrace();
        ctx.close();
    }
}
