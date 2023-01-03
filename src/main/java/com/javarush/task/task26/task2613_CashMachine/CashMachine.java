package com.javarush.task.task26.task2613_CashMachine;

import com.javarush.task.task26.task2613_CashMachine.command.CommandExecutor;
import com.javarush.task.task26.task2613_CashMachine.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine {
    public static final String RESOURCE_PATH = ".resources.";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        try {
            CommandExecutor.execute(Operation.LOGIN);
            while (true) {
                Operation operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
                if (operation.equals(Operation.EXIT)) break;
            }
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
