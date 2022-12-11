package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;
import com.javarush.task.task31.task3110_Archiver.ZipFileManager;
import com.javarush.task.task31.task3110_Archiver.exception.WrongZipFileException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Извлечение архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter the full path of file or directory to extract");
            String outputFolder = ConsoleHelper.readString();
            Path pathToExtract = Paths.get(outputFolder);
            zipFileManager.extractAll(pathToExtract);
            ConsoleHelper.writeMessage("Архив распакован.");
        } catch (WrongZipFileException e) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }

    }
}
