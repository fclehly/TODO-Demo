package com.fwz.java11.io;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.charset.Charset;

public class IoDemo {
    private void listAllFiles(File file) {

        if (file == null) {
            return;
        }
        if (file.isFile()) {
            System.out.println(file.getAbsolutePath());
            return;
        }
        for (File item : file.listFiles()) {
            listAllFiles(item);
        }
    }

    private void copyFile(String srcFilePath, String destFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(srcFilePath);
        FileOutputStream fileOutputStream = new FileOutputStream(destFilePath);
        byte[] buffer = new byte[20 * 1024];
        int count;

        while ((count = fileInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, count);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

    private void charsetDemo() {
        String str1 = "中文啊";
        byte[] bytes = str1.getBytes(Charset.forName("UTF-8"));
        String str2 = new String(bytes, Charset.forName("UTF-8"));
        System.out.println(str2);
    }

    private void readFileContent(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
        fileReader.close();
    }

    private void serializeObj2File(String filePath) throws IOException, ClassNotFoundException {
        A a = new A(1, "234", "1233434");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        objectOutputStream.writeObject(a);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
        A a1 = (A) objectInputStream.readObject();
        System.out.println(a1);

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String tempFilePath = "/Users/fwz/tmp/temp.txt";
        IoDemo demo = new IoDemo();
        demo.listAllFiles(new File("."));
        demo.copyFile(tempFilePath, "/Users/fwz/tmp/temp_copy.txt");
        demo.charsetDemo();
        demo.readFileContent(tempFilePath);
        demo.serializeObj2File(tempFilePath + ".1");
    }

    @Data
    @AllArgsConstructor
    private static class A implements Serializable {
        private int a;
        private String b;
        private transient String c;
    }


}
