package com.netty.beat.impl;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/26.
 */
@ChannelHandler.Sharable
public class BeatCheckServerHandler extends ChannelInboundHandlerAdapter{

    private Logger logger = Logger.getLogger(BeatCheckServerHandler.class);

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel)ctx.channel();
        connectionPool.put(socketChannel.id().asLongText(),socketChannel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseObj responseObj = (ResponseObj)msg;
        logger.info("the check channel id is :"+responseObj.getChannelId());
        if(Type.BEAT_CHECK.toString().equals(responseObj.getType())){
            if("ok".equals(responseObj.getMsg())){
                logger.info("连接正常..................");
            }else{
                SocketChannel socketChannel = (SocketChannel)ctx.channel();
                connectionPool.remove(socketChannel.id().asLongText());
                logger.error("连接异常,error msg:"+responseObj.getMsg());
            }
        }else if(Type.RESPONSE_TIME_CHECK.toString().equals(responseObj.getType())){
            // Todo
            logger.info("===========待实现=================");
        }else {
            logger.info("===========操作类型错误=================");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel)ctx.channel();
        connectionPool.remove(socketChannel.id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getStackTrace());
        /*SocketChannel socketChannel = (SocketChannel)ctx.channel();
        connectionPool.remove(socketChannel.id().asLongText());*/
    }
}
