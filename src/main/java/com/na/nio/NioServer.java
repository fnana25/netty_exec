package com.na.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
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
 * nio server
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>();
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8090));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){

            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "【"+ UUID.randomUUID().toString()+"】";
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            client = (SocketChannel)selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            int count = client.read(readBuffer);
                            if(count > 0){
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + receivedMessage);

                                String senderKey = null;
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    if(entry.getValue() == client){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel value = entry.getValue();

                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    buffer.put((senderKey + ":" + receivedMessage).getBytes());

                                    buffer.flip();
                                    value.write(buffer);
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
