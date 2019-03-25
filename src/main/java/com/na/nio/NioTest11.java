package com.na.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering与Gathering
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest11 {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long read = socketChannel.read(buffers);
                bytesRead += read;

                Arrays.stream(buffers).map(buffer -> "position : " + buffer.position() + "limit : " + buffer.limit())
                        .forEach(System.out::println);

                Arrays.stream(buffers).forEach(ByteBuffer::flip);
            }

            int bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long write = socketChannel.write(buffers);
                bytesWritten += write;
            }
            Arrays.asList(buffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead : " + bytesRead + "byteWrite : " + bytesWritten + "messageLength : " + messageLength);
        }
    }
}
