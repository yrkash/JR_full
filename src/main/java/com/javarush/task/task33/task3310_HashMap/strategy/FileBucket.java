package com.javarush.task.task33.task3310_HashMap.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            this.path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException ignore) {
//            throw new RuntimeException(e);
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException ignore) {
//            throw new RuntimeException(e);
        }
        return 0;
    }

    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path));
            outputStream.writeObject(entry);
            outputStream.close();
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }

    public Entry getEntry() {
        if (getFileSize() == 0) return null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path));
            Entry entry =  (Entry) inputStream.readObject();
            inputStream.close();
            return entry;
        } catch (IOException e) {
//            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
        }
        return null;
    }
    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }
}
