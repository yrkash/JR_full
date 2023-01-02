package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class WithdrawCommand implements Command{
    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        ConsoleHelper.writeMessage("Enter withdrawal amount");
        int expectedAmount;
        Map<Integer, Integer> denominationsAfterWithdraw = new TreeMap<>(Collections.reverseOrder());
        while (true) {
            try {
                expectedAmount = Integer.parseInt(ConsoleHelper.readString());
                if (currencyManipulator.isAmountAvailable(expectedAmount)) {
                    denominationsAfterWithdraw =
                            currencyManipulator.withdrawAmount(expectedAmount);
                    break;
                } else {
                    ConsoleHelper.writeMessage("Enter withdrawal amount");
                }
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage("Enter withdrawal amount");
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage("Enter ANOTHER withdrawal amount");
            }
        }
        for (Map.Entry<Integer, Integer> entry: denominationsAfterWithdraw.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }


    }
}
