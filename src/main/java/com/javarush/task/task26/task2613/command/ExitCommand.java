package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

class ExitCommand implements Command{
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("Do you want to exit ? (y - yes, n - no)");
        String answer = ConsoleHelper.readString();
        if (answer.equals("y")) ConsoleHelper.writeMessage("GoodBye");

    }
}
