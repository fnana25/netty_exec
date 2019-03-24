package com.na.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * NioTest1 buffer练习
 * note:
 * 1，capacity生成后就不变了
 * 2,随机数生成推荐用SecureRandom
 * 3，sout System.out.println()快捷键  fori for循环快捷键 psvm main函数快捷键
 *
 * @author fengna
 * @date 19/3/24 20:12
 */
public class NioTest1 {

    public static void main(String[] args) {

        int dataSize = 5;
        IntBuffer buffer = IntBuffer.allocate(10);

        System.out.println(buffer.capacity());

        for (int i = 0; i < dataSize; i++) {
            int num = new SecureRandom().nextInt(20);
            buffer.put(num);
        }

        System.out.println("before flip limit:" + buffer.limit());

        buffer.flip();

        System.out.println("after flip limit:" + buffer.limit());

        System.out.println("enter while loop");

        while (buffer.hasRemaining()) {
            System.out.println("position : " + buffer.position());
            System.out.println("capacity : " + buffer.capacity());
            System.out.println("limit : " + buffer.limit());

            System.out.println(buffer.get());
        }
    }
}
