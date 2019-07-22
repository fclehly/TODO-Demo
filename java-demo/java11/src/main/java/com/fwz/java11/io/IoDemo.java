package com.fwz.java11.io;

import java.io.File;

public class IoDemo {
    public static void main(String[] args) {
        IoDemo demo = new IoDemo();
        demo.listAllFiles(new File("."));
    }

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

}
