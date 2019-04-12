package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Demo class
 *
 * @author fengna
 * @date 2019/4/9
 */
public class NioFile1 {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fileChannel.read(buffer);

        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.print((char)buffer.get());
        }
        inputStream.close();
    }

}
