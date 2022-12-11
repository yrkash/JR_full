package com.javarush.task.task31.task3110_Archiver;

import com.javarush.task.task31.task3110_Archiver.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> ALL_KNOWN_COMMANDS_MAP = new HashMap<>();
    static {
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CREATE, new ZipCreateCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.ADD, new ZipAddCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.REMOVE, new ZipRemoveCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXTRACT, new ZipExtractCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CONTENT, new ZipContentCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXIT, new ExitCommand());
    }
    public static void execute (Operation operation) throws Exception {
        for(Map.Entry<Operation, Command> entry : ALL_KNOWN_COMMANDS_MAP.entrySet()){

            if (entry.getKey() == operation) entry.getValue().execute();
        }
    }
    private CommandExecutor() {
    }
}
