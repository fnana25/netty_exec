package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Demo class
 *
 * @author fengna
 * @date 2019/4/8
 */
public class NioServerTest {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {


        ServerSocketChannel serverSocketChannel = null;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8090));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(key -> {
                    final SocketChannel client;
                    try {
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);

                            clientMap.put("【" + UUID.randomUUID().toString() + "】", client);
                        } else if (key.isReadable()) {
                            client = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(512);
                            int count = client.read(buffer);
                            if (count > 0) {
                                buffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String message = String.valueOf(charset.decode(buffer).array());
                                System.out.println(client + " : " + message);

                                String sendClient = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (entry.getValue() == client) {
                                        sendClient = entry.getKey();
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    byteBuffer.put((sendClient + " : " + message).getBytes());
                                    byteBuffer.flip();
                                    entry.getValue().write(byteBuffer);
                                }
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
