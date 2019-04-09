package nio;

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
 * Demo class
 *
 * @author fengna
 * @date 2019/4/8
 */
public class NioClientTest {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8090));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {

            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(key -> {
                try {
                    if (key.isConnectable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                            ByteBuffer buffer = ByteBuffer.allocate(512);
                            buffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            buffer.flip();
                            client.write(buffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true) {
                                    buffer.clear();
                                    String message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                                    buffer.put(message.getBytes());
                                    buffer.flip();
                                    client.write(buffer);
                                }
                            });
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = client.read(buffer);
                        if (count > 0) {
                            System.out.println("received : " + new String(buffer.array(), 0, count));
                        }
                    }
                    selectionKeys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
