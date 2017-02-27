package com.jdk.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by szc on 17/1/18.
 */
public class HttpServer {

    public void start() throws IOException{
        boolean isShutDown = false;
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8081,1024,InetAddress.getByName("127.0.0.1"));
            while (!isShutDown){
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] bytes = new byte[6];
                bufferedInputStream.read(bytes);
                String s = new String(bytes);
                if(s.equals("logout")){
                    isShutDown = true;
                }
            }
            System.out.print("logout--------");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws Exception{
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
