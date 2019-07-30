package com.fwz.java11.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SocketDemo {

    private void urlStream() throws IOException {
        URL url = new URL("https://www.github.com");
        InputStream inputStream = url.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    public static void main(String[] args) throws Exception {
        SocketDemo demo = new SocketDemo();
        demo.urlStream();
    }
}
