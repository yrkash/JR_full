package com.javarush.task.task26.task2613_CashMachine.command;

import com.javarush.task.task26.task2613_CashMachine.ConsoleHelper;
import com.javarush.task.task26.task2613_CashMachine.CurrencyManipulator;
import com.javarush.task.task26.task2613_CashMachine.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613_CashMachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle("deposit");
//    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.deposit");
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currency = ConsoleHelper.askCurrencyCode();
        String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(currency);
        try {
            int denomination = Integer.parseInt(validTwoDigits[0]);
            int count = Integer.parseInt(validTwoDigits[1]);
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
            currencyManipulator.addAmount(denomination,count);
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination * count, currency));
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
