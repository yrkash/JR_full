package com.javarush.task.task31.task3111_multiSearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
Продвинутый поиск файлов
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        SearchFileVisitor searchFileVisitor = new SearchFileVisitor();

//        searchFileVisitor.setPartOfName("amigo");
        searchFileVisitor.setPartOfContent("programmer");
//        searchFileVisitor.setMinSize(50);
//        searchFileVisitor.setMaxSize(10000);

        Files.walkFileTree(Paths.get("c:/temp/catalog"), searchFileVisitor);

        List<Path> foundFiles = searchFileVisitor.getFoundFiles();
        for (Path file : foundFiles) {
            System.out.println(file);
        }
    }

}
