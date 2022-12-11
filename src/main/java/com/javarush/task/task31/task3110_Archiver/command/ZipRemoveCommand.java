package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;
import com.javarush.task.task31.task3110_Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Удаление файлов из архива.");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the file path to delete");
        String deletedFile = ConsoleHelper.readString();
        Path pathToDelete = Paths.get(deletedFile);
        zipFileManager.removeFile(pathToDelete);
    }
}
