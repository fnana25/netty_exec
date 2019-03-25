package com.na.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer类型化的put与get方法
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest5 {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(10);
        byteBuffer.putLong(20);
        byteBuffer.putDouble(99.222);
        byteBuffer.putChar('a');
        byteBuffer.putShort((short) 2);
        byteBuffer.putFloat(23.8f);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getFloat());

    }
}
