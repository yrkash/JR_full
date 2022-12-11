package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;
import com.javarush.task.task31.task3110_Archiver.ZipFileManager;
import com.javarush.task.task31.task3110_Archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Создание архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter the full path of file or directory to archive");
            String strToArchive = ConsoleHelper.readString();
            Path pathToArchive = Paths.get(strToArchive);
            zipFileManager.createZip(pathToArchive);
            ConsoleHelper.writeMessage("Архив создан.");
        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }

    }
}
