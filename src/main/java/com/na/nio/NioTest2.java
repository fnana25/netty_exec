package com.na.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NioTest2
 *
 * @author fengna
 * @date 19/3/24 21:07
 */
public class NioTest2 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        fileChannel.read(buffer);

        buffer.flip();

        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            System.out.println("Character : " + (char) b);
        }
        fileInputStream.close();
    }
}
