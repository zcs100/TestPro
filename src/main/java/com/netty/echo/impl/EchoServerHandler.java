package com.netty.echo.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/6.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    private Logger logger = Logger.getLogger(EchoServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("EchoServerHandler channelRead method ........");
        /*ByteBuf buf = (ByteBuf)msg;
        logger.info(buf.toString(CharsetUtil.UTF_8));*/
        logger.info(msg.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("EchoServerHandler channelReadComplete method ........");
        ctx.writeAndFlush("First netty toooo").addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("EchoServerHandler exceptionCaught method ........");
        cause.printStackTrace();
        ctx.close();
    }


}
