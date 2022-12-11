package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;
import com.javarush.task.task31.task3110_Archiver.ZipFileManager;

import java.nio.file.Paths;

public abstract class ZipCommand implements Command{
    public ZipFileManager getZipFileManager() throws Exception{
        ConsoleHelper.writeMessage("Enter the full path of the archive");
        String fullPathStr = ConsoleHelper.readString();
        return new ZipFileManager(Paths.get(fullPathStr));
    }
}
