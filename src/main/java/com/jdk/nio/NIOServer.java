package com.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by szc on 17/9/3.
 */
public class NIOServer {

    private Selector selector;

    public void initServer(int port) throws IOException{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException{
        System.out.println("服务端已启动");
        while (true){
            selector.select();
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey selectionKey = (SelectionKey)ite.next();
                ite.remove();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.write(ByteBuffer.wrap(new String("已连接到服务端了").getBytes()));
                    socketChannel.register(selector,SelectionKey.OP_READ);
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
        ByteBuffer outMsg = ByteBuffer.wrap("已收到客户端发到消息,哈哈....".getBytes());
        socketChannel.write(outMsg);
    }

    public static void main(String[] args) throws IOException{
        NIOServer nioServer = new NIOServer();
        nioServer.initServer(8080);
        nioServer.listen();
    }
}
