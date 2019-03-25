package com.na.nio;

import java.nio.ByteBuffer;

/**
 * Slice Buffer与原有buffer共享相同的底层数组
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest6 {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte)i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();

        System.out.println("buffer capacity : " + buffer.capacity());
        System.out.println("buffer position : " + buffer.position());
        System.out.println("buffer limit : " + buffer.limit());

        System.out.println("sliceBuffer capacity : " + sliceBuffer.capacity());
        System.out.println("sliceBuffer position : " + sliceBuffer.position());
        System.out.println("sliceBuffer limit : " + sliceBuffer.limit());

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            System.out.println("sliceBuffer : " + sliceBuffer.get(i));
        }

        for(int i = 0; i < sliceBuffer.capacity(); ++i) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while(buffer.hasRemaining()) {
            System.out.println("buffer : " + buffer.get());
        }
    }
}
