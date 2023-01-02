package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

public class LoginCommand implements Command{

    private static final String CARD_NUMBER = "123456789012";
    private static final String PIN_NUMBER = "1234";
    @Override
    public void execute() throws InterruptOperationException {
        String cardNumber;
        String pin;
        while (true) {
            ConsoleHelper.writeMessage("Enter number of card");
            cardNumber = ConsoleHelper.readString();
            ConsoleHelper.writeMessage("Enter pin code");
            pin = ConsoleHelper.readString();
            if (cardNumber.matches("\\d{12}") && pin.matches("\\d{4}")) {
                if (cardNumber.equals(CARD_NUMBER) && pin.equals(PIN_NUMBER)) {
                    ConsoleHelper.writeMessage("Verification completed");
                    break;
                } else {
                    ConsoleHelper.writeMessage("Not valid number or pin. Try again");
                }
            } else {
                ConsoleHelper.writeMessage("Not valid number or pin. Try again");
            }
        }

    }
}
