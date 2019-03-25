package com.na.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio读写文件
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest4 {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true) {

            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println("read : " + read);
            if (read == -1) {
                break;
            }

            buffer.flip();
            outputChannel.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
