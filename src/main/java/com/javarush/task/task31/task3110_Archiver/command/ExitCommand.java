package com.javarush.task.task31.task3110_Archiver.command;

import com.javarush.task.task31.task3110_Archiver.ConsoleHelper;

public class ExitCommand implements Command{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
