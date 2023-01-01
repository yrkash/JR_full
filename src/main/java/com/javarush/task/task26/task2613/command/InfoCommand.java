package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;

class InfoCommand implements Command{
    @Override
    public void execute() {
        boolean hasAnyCurrency = false;
        Collection<CurrencyManipulator> manipulatorList = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        for (CurrencyManipulator manipulator: manipulatorList) {
            if (manipulator.hasMoney()) {
                hasAnyCurrency = true;
                ConsoleHelper.writeMessage(manipulator.getCurrencyCode()
                                + " - " + manipulator.getTotalAmount());
            }
        }
        if (!hasAnyCurrency) ConsoleHelper.writeMessage("No money available.");
    }
}
