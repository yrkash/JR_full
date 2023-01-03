package com.javarush.task.task26.task2613_CashMachine.command;

import com.javarush.task.task26.task2613_CashMachine.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}
