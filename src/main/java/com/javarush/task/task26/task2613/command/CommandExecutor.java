package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.Operation;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private final static Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    private CommandExecutor() {
    }

    static {
        allKnownCommandsMap.put(Operation.INFO, new InfoCommand());
        allKnownCommandsMap.put(Operation.DEPOSIT, new DepositCommand());
        allKnownCommandsMap.put(Operation.WITHDRAW, new WithdrawCommand());
        allKnownCommandsMap.put(Operation.EXIT, new ExitCommand());
        allKnownCommandsMap.put(Operation.LOGIN, new LoginCommand());
    }
    public static final void execute(Operation operation) throws InterruptOperationException {
        Command executeCommand = allKnownCommandsMap.get(operation);
        executeCommand.execute();
    }
}
