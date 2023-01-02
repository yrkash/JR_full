package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        try {
            while (true) {
                Operation operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
                if (operation.equals(Operation.EXIT)) break;
            }
        } catch (InterruptOperationException e) {
            ConsoleHelper.writeMessage("GoodBye");
        }
    }
}
