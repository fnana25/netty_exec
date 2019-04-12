package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Demo class
 *
 * @author fengna
 * @date 2019/4/9
 */
public class NioFile3 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
        FileChannel fileInputChannel = fileInputStream.getChannel();
        FileChannel fileOutputChanel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true){
            buffer.clear();
            int count = fileInputChannel.read(buffer);
            if(count == -1){
                break;
            }
            buffer.flip();
            fileOutputChanel.write(buffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
