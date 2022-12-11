package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;
import com.javarush.task.task31.task3110_Archiver.ZipFileManager;
import com.javarush.task.task31.task3110_Archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Добавление файла в архив.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter the file path to add");
            String addedFile = ConsoleHelper.readString();
            Path pathToAdd = Paths.get(addedFile);
            zipFileManager.addFile(pathToAdd);
        } catch (PathIsNotFoundException e) {

        }

    }
}
