package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/*
Что внутри папки?
*/

public class Solution {



    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String directory =reader.readLine();
        Path directoryPath = Paths.get(directory);
        if (!Files.isDirectory(directoryPath)) {
             System.out.println(directory + " - не папка");
             return;
        } else {
            AtomicInteger countOfDirectory = new AtomicInteger();
            AtomicInteger countOfFiles = new AtomicInteger();
            AtomicLong amountOfBytes = new AtomicLong();
            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(directoryPath)) countOfDirectory.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    amountOfBytes.addAndGet(attrs.size());
                    countOfFiles.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Всего папок - " + countOfDirectory.get());
            System.out.println("Всего файлов - " + countOfFiles.get());
            System.out.println("Общий размер - " + amountOfBytes.get());
        }
    }

}
