package com.megetood.util;

import java.io.File;

public class FileUtil {

    public static String getSharedDataDir() {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");

        return dir.toString() + File.separator;
    }

    public static String getDataDir(Class c) {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");

        for (String s : c.getName().split("\\.")) {
            dir = new File(dir, s);
            if (dir.isDirectory() == false)
                dir.mkdir();
        }
        System.out.println("Using data directory: " + dir.toString());
        return dir.toString() + File.separator;
    }

    public static String GetOutputFilePath(String inputFilePath) {
        String extension = "";
        int i = inputFilePath.lastIndexOf('.');
        if (i > 0) {
            extension = inputFilePath.substring(i + 1);
        }
        if (inputFilePath.contains(".")) {
            inputFilePath = inputFilePath.substring(0, inputFilePath.lastIndexOf("."));
        }

        return inputFilePath + "_out_." + extension;
    }

}
