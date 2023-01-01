package com.javarush.task.task26.task2613;

import java.util.Locale;

public class CashMachine {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        String currency = ConsoleHelper.askCurrencyCode();
        String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(currency);
        int denominations = Integer.parseInt(validTwoDigits[0]);
        int count = Integer.parseInt(validTwoDigits[1]);
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);;
        currencyManipulator.addAmount(denominations,count);
    }
}
