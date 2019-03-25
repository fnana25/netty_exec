package com.na.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 字符编解码
 *
 * @author fengna
 * @date 2019/3/25
 */
public class NioTest13 {

    public static void main(String[] args) throws IOException {

        RandomAccessFile inputFile = new RandomAccessFile("NioTest13_In.txt", "r");
        RandomAccessFile outputFile = new RandomAccessFile("NioTest13_Out.txt", "rw");

        long length = new File("NioTest13_In.txt").length();

        FileChannel inputFileChannel = inputFile.getChannel();
        FileChannel outputFileChannel = outputFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);

        System.out.println("===============");

        Charset.availableCharsets().forEach((k, v) -> System.out.println("k : " + k + "v : " + v));

        System.out.println("===============");

        Charset charset = Charset.forName("iso-8859-1");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer charBuffer = decoder.decode(inputData);

        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        outputFileChannel.write(byteBuffer);

        inputFile.close();
        outputFile.close();
    }
}
