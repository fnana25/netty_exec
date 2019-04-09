package com.na.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * nio client
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioClient {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8090));

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectKey : selectionKeys) {
                    if (selectKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectKey.channel();

                        if (client.isConnectionPending()) {
                            client.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true) {
                                    try{
                                        writeBuffer.clear();
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(input);

                                        String message = bufferedReader.readLine();
                                        writeBuffer.put(message.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(byteBuffer);
                        if (count > 0) {
                            String receivedMessage = new String(byteBuffer.array(), 0, count);
                            System.out.println("received : " + receivedMessage);
                        }
                    }

                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
