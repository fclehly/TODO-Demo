package com.fwz.java11.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioDemo {
    public static void fastCopy(String src, String dest) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileChannel fileInputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(dest);
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            int r = fileInputChannel.read(byteBuffer);
            if (r == -1) {
                break;
            }
            byteBuffer.flip();
            fileOutputChannel.write(byteBuffer);
            byteBuffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        final String tempFilePath = "/Users/fwz/tmp/temp.txt";
        File file = new File(".");
        System.out.println(file.getAbsolutePath());
        fastCopy("java11/pom.xml", tempFilePath);
    }
}
