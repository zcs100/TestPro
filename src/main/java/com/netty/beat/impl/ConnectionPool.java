package com.netty.beat.impl;

import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;

import java.util.AbstractMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by szc on 16/12/26.
 */
public class ConnectionPool extends ConcurrentHashMap<String,SocketChannel>{

    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool = null;

    public static ConnectionPool getConnectionPool(){
        if(connectionPool == null){
            synchronized (logger){
                if(connectionPool == null){
                    connectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    @Override
    public SocketChannel put(String key, SocketChannel value) {
        logger.info("the object("+key+") is put in pool!!!!!!!");
        return super.put(key, value);
    }

    @Override
    public SocketChannel remove(Object key) {
        logger.info("the object("+key+") is removed from pool!!!!!!");
        return super.remove(key);
    }

}
