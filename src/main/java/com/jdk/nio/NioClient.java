package com.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by szc on 17/9/3.
 */
public class NioClient {

    private Selector selector;

    public void initClient(String ip,int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(ip,port));
        this.selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {
        System.out.println("客户端已启动");
        while (true) {
            selector.select();
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) ite.next();
                ite.remove();
                if(selectionKey.isConnectable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);
                    channel.write(ByteBuffer.wrap("已经可以连接,发送给服务端一条消息".getBytes()));
                    channel.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    read(selectionKey);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.allocate(64);
        socketChannel.read(buf);
        byte[] data = buf.array();
        String msg = new String(data);
        System.out.println(msg);
        ByteBuffer outMsg = ByteBuffer.wrap("已收到服务端发到消息,嘻嘻....".getBytes());
        socketChannel.write(outMsg);
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.initClient("localhost",8080);
        client.listen();
    }
}
