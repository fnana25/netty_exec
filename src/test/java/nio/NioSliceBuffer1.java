package nio;

import java.nio.ByteBuffer;
import java.util.zip.DeflaterOutputStream;

/**
 * Demo class
 *
 * @author fengna
 * @date 2019/4/9
 */
public class NioSliceBuffer1 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.get(i));
        }
        System.out.println();
        System.out.println(buffer.limit() + ":" + buffer.capacity() + ":" + buffer.position());
        buffer.position(3);
        buffer.limit(8);

        ByteBuffer sliceBuffer = buffer.slice();

        System.out.println(sliceBuffer.limit() + ":" + sliceBuffer.capacity() + ":" + sliceBuffer.position());

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i,b);
        }

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            System.out.print(sliceBuffer.get(i));
        }
        System.out.println();
        buffer.position(0);
        buffer.limit(buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.get(i));
        }
    }
}
