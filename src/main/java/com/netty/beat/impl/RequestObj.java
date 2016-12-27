package com.netty.beat.impl;

import java.io.Serializable;

/**
 * Created by szc on 16/12/26.
 */
public class RequestObj implements Serializable{

    private String channelId;

    /**
     * 请求类型 {@link com.netty.beat.impl.Type}
     */
    private String type;
    /**
     * 描述
     */
    private String msg;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
