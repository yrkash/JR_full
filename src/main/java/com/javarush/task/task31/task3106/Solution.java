package com.javarush.task.task31.task3106;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/

public class Solution {
    public static void main(String[] args) throws IOException {

        File resultFile = new File(args[0]);
        if (!resultFile.exists()) resultFile.createNewFile();

        List<String> fileNameParts = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            fileNameParts.add(args[i]);
        }
        Collections.sort(fileNameParts);
        List<FileInputStream> inputStreamList = new ArrayList<>();
        for (String fileName: fileNameParts) {
            inputStreamList.add(new FileInputStream(fileName));
        }

        try (ZipInputStream zipInputStream =
                     new ZipInputStream
                             (new SequenceInputStream
                                     (Collections.enumeration(inputStreamList)))) {

            while (true) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) break;
                try(FileOutputStream fileOutputStream = new FileOutputStream(resultFile)) {
                    byte[] buffer =new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                }
            }
        } catch (IOException ignore) {
            ignore.getStackTrace();
        }
    }
}
