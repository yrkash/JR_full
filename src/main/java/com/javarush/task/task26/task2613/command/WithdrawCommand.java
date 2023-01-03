package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

class WithdrawCommand implements Command{
    private ResourceBundle res = ResourceBundle.getBundle("withdraw");
    //    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.withdraw");
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int expectedAmount;
        Map<Integer, Integer> denominationsAfterWithdraw = new TreeMap<>(Collections.reverseOrder());
        while (true) {
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                expectedAmount = Integer.parseInt(ConsoleHelper.readString());
                if (currencyManipulator.isAmountAvailable(expectedAmount)) {
                    denominationsAfterWithdraw =
                            currencyManipulator.withdrawAmount(expectedAmount);
                    break;
                } else {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                }
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
        for (Map.Entry<Integer, Integer> entry: denominationsAfterWithdraw.entrySet()) {
            ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"),expectedAmount,currencyCode));


    }
}
