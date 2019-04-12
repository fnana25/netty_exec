package nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Demo class
 *
 * @author fengna
 * @date 2019/4/9
 */
public class NioBuffer1 {

    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(10);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(new SecureRandom().nextInt());
        }

        System.out.println(intBuffer.limit());

        intBuffer.flip();

        System.out.println(intBuffer.limit());

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());

            System.out.println(intBuffer.limit() + ":" + intBuffer.capacity() + ":" + intBuffer.position());
            System.out.println("--------------");
        }
    }
}
