package com.javarush.task.task31.task3111_multiSearch;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName = new String();
    private String partOfContent = new String();
    private int minSize = 0;
    private int maxSize  = Integer.MAX_VALUE;

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    private List<Path> foundFiles = new ArrayList<>();
    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }



    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        String fileName = file.toString();
        boolean isNameContains = file.toString().contains(partOfName);
        boolean isContentContains = new String(content).contains(partOfContent);
        boolean isContentLessThan = content.length < maxSize;
        boolean isContentMoreThan = content.length > minSize;

        if (isNameContains && isContentContains && isContentLessThan && isContentMoreThan) {
            foundFiles.add(file);
        }

        return super.visitFile(file, attrs);
    }
}
