package com.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        //вызов рекурсивного обхода подкаталогов
        try {
            MainFile.findFiles(dir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findFiles(File file) throws Exception {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = list.length; --i >= 0; ) {
                findFiles(list[i]);
            }
        } else {
            System.out.println("\t" + file.getCanonicalPath());
        }
    }
}
