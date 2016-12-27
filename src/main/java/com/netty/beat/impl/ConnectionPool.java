package com.netty.beat.impl;

import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;

import java.util.AbstractMap;
import java.util.Set;

/**
 * Created by szc on 16/12/26.
 */
public class ConnectionPool extends AbstractMap<String,SocketChannel>{

    private Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool;

    public static ConnectionPool getConnectionPool(){
        if(connectionPool == null){
            synchronized (ConnectionPool.class){
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

    public Set<Entry<String, SocketChannel>> entrySet() {
        return this.entrySet();
    }
}
