package com.javarush.task.task26.task2613_CashMachine.command;

import com.javarush.task.task26.task2613_CashMachine.ConsoleHelper;
import com.javarush.task.task26.task2613_CashMachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle("login");
//    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.login");

    private ResourceBundle validCreditCards = ResourceBundle.getBundle("verifiedCards");
//    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.verifiedCards");
    @Override
    public void execute() throws InterruptOperationException {
        String cardNumber;
        String pin;
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            cardNumber = ConsoleHelper.readString();
            pin = ConsoleHelper.readString();
            if (cardNumber.matches("\\d{12}") && pin.matches("\\d{4}")) {
                if (validCreditCards.containsKey(cardNumber) && pin.equals(validCreditCards.getString(cardNumber))) {
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"),cardNumber));
                    break;
                } else {
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"),cardNumber));
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"),cardNumber));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
        }

    }
}
