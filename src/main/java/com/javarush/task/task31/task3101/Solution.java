package com.javarush.task.task31.task3101;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/*
Проход по дереву файлов
*/

public class Solution {


    public static void main(String[] args) {
        String path = args[0];
        String resultFileAbsolutePath = args[1];
        try {
            File resultFile = new File(args[1]);
            File dest = new File(resultFile.getParent() + "/allFilesContent.txt");
            if (FileUtils.isExist(dest)) {
                FileUtils.deleteFile(dest);
            }
            FileUtils.renameFile(resultFile, dest);
            List<Path> fileList = getFileList(path);
            try(FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
                for (Path filePath: fileList) {
                    if (filePath.toFile().length() <= 50) {
                        FileInputStream fileInputStream = new FileInputStream(filePath.toFile());
                        while (fileInputStream.available() > 0) {
                            fileOutputStream.write(fileInputStream.read());
                        }
                        fileOutputStream.write("\n".getBytes());
                        fileInputStream.close();
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    public static List<Path> getFileList(String path) throws IOException {
        List<Path> fileList = new ArrayList<>();
        collectFileList(fileList, Paths.get(path));
        return fileList;
    }


    private static List<Path> collectFileList(List<Path> fileList, Path path) throws IOException {
        if (Files.isRegularFile(path)) {
            fileList.add(path);
        }
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path file : directoryStream) {
                    collectFileList(fileList, file);
                }
            }
        }
        return fileList;
    }

}
