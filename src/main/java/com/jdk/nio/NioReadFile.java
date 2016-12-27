package com.jdk.nio;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by szc on 16/12/19.
 */
public class NioReadFile {

    private Logger logger = Logger.getLogger(NioReadFile.class);

    @Test
    public void testReadFile() throws Exception {
        String path = "/Users/szc/TestWorkPlace/TestPro/src/main/java/com/jdk/nio/testFileRead";
        int alloc = 1024;
        //readFileByBlock(path,alloc);
        readFileByLine(path, alloc);
    }

    /**
     * 按块读取
     *
     * @param path
     * @param alloc
     * @throws Exception
     */
    public void readFileByBlock(String path, int alloc) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("文件不存在........");
        }
        RandomAccessFile accessFilefile = new RandomAccessFile(file, "r");
        FileChannel fileChannel = accessFilefile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(alloc);

        int len;
        while ((len = fileChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            byte[] bytes = new byte[len];
            byteBuffer.get(bytes, 0, len);
            logger.info(new String(bytes));
            byteBuffer.clear();
        }
        fileChannel.close();
    }

    /**
     * 按行读取,解决乱码问题(http://blog.csdn.net/v123411739/article/details/50620289)
     *
     * @param path
     * @param alloc
     * @throws Exception
     */
    public void readFileByLine(String path, int alloc) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("文件不存在........");
        }
        RandomAccessFile accessFilefile = new RandomAccessFile(file, "r");
        FileChannel fileChannel = accessFilefile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(alloc);

        byte[] lineByte = new byte[0];
        String encode = "UTF-8";

        //temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
        //并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
        byte[] temp = new byte[0];
        while (fileChannel.read(byteBuffer) != -1) {//fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
            int rSize = byteBuffer.position();//读取结束后的位置，相当于读取的长度
            byte[] bs = new byte[rSize];//用来存放读取的内容的数组
            byteBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
            byteBuffer.get(bs);//相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
            byteBuffer.clear();

            int startNum = 0;
            int LF = 10;//换行符
            int CR = 13;//回车符
            boolean hasLF = false;//是否有换行符
            for (int i = 0; i < rSize; i++) {
                if (bs[i] == LF) {
                    hasLF = true;
                    int tempNum = temp.length;
                    int lineNum = i - startNum;
                    lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符

                    System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
                    temp = new byte[0];
                    System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]

                    String line = new String(lineByte, 0, lineByte.length, encode);//一行完整的字符串(过滤了换行和回车)
                    logger.info(line);
                    //过滤回车符和换行符
                    if (i + 1 < rSize && bs[i + 1] == CR) {
                        startNum = i + 2;
                    } else {
                        startNum = i + 1;
                    }
                }
            }
            if (hasLF) {
                temp = new byte[bs.length - startNum];
                System.arraycopy(bs, startNum, temp, 0, temp.length);
            } else {//兼容单次读取的内容不足一行的情况
                byte[] toTemp = new byte[temp.length + bs.length];
                System.arraycopy(temp, 0, toTemp, 0, temp.length);
                System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                temp = toTemp;
            }
        }
        if (temp != null && temp.length > 0) {//兼容文件最后一行没有换行的情况
            String line = new String(temp, 0, temp.length, encode);
            logger.info(line);
        }
        fileChannel.close();
    }

}
