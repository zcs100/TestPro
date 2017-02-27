package com.jdk.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by szc on 17/1/18.
 */
public class HttpClient {

    public static void main(String[] args) throws IOException{
        Socket socket = null;
        try{
            socket = new Socket("127.0.0.1",8081);
            OutputStream out = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
            bufferedOutputStream.write("logout".getBytes());
            bufferedOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
