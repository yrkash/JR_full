package com.javarush.task.task26.task2613_CashMachine.command;

import com.javarush.task.task26.task2613_CashMachine.ConsoleHelper;
import com.javarush.task.task26.task2613_CashMachine.CurrencyManipulator;
import com.javarush.task.task26.task2613_CashMachine.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle( "info");
//    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.info");
    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean hasAnyCurrency = false;
        Collection<CurrencyManipulator> manipulatorList = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        for (CurrencyManipulator manipulator: manipulatorList) {
            if (manipulator.hasMoney()) {
                hasAnyCurrency = true;
                ConsoleHelper.writeMessage("\t" + manipulator.getCurrencyCode() + " - " + manipulator.getTotalAmount());
            }
        }
        if (!hasAnyCurrency) ConsoleHelper.writeMessage(res.getString("no.money"));
    }
}
