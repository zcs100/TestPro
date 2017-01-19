package com.netty.beat.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;

/**
 * Created by szc on 16/12/26.
 */
public class BeatCheckClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestObj requestObj = (RequestObj)msg;
        logger.info("the check channel id is :"+requestObj.getChannelId());
        if(Type.BEAT_CHECK.toString().equals(requestObj.getType())){
            ResponseObj responseObj = new ResponseObj();
            responseObj.setType(Type.BEAT_CHECK.toString());
            responseObj.setChannelId(requestObj.getChannelId());
            if("ok".equals(requestObj.getMsg())){
                responseObj.setMsg("ok");
            }else{
                responseObj.setMsg("the connection is not connected...........");
            }
            ctx.writeAndFlush(responseObj);
        }else if(Type.RESPONSE_TIME_CHECK.toString().equals(requestObj.getType())){
            // Todo
            logger.info("===========待实现=================");
        }else {
            logger.info("===========操作类型错误=================");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       logger.error(cause.getStackTrace());
    }
}
