package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

class DepositCommand implements Command{
    @Override
    public void execute() throws InterruptOperationException {
        String currency = ConsoleHelper.askCurrencyCode();
        String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(currency);
        int denominations = Integer.parseInt(validTwoDigits[0]);
        int count = Integer.parseInt(validTwoDigits[1]);
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
        currencyManipulator.addAmount(denominations,count);
    }
}
