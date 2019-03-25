package com.na.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio写文件
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileInputStream = new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte[] message = "hello world welcome 你好！".getBytes();

        for (byte b : message) {
            buffer.put(b);
        }

        buffer.flip();
        fileChannel.write(buffer);
        fileInputStream.close();
    }
}
