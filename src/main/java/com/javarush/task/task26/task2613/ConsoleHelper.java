package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static ResourceBundle res = ResourceBundle.getBundle("common");
//    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.common");

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = new String();
        while (true) {
            try {
                result = bis.readLine();
                if (result.toUpperCase().equals("EXIT")) {
//                    writeMessage(res.getString("the.end"));
                    throw new InterruptOperationException();
                }
                break;
            } catch (IOException ignore) {
            }
        }
        return result;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String result = new String();
        while (true) {
            result = readString();
            if (result.length() != 3) {
                writeMessage(res.getString("choose.currency.code"));
            } else {
                break;
            }
        }
        return result.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"),currencyCode));
        String denomination;
        String count;
        String result;
        String[] validTwoDigits = new String[2];
        while (true) {
            result = readString();
            if (!result.matches("^[1-9]\\d*\\s[1-9]\\d*")) {
                writeMessage(String.format(res.getString("choose.denomination.and.count.format"),currencyCode));
            } else {
                denomination = result.split(" ")[0];
                count = result.split(" ")[1];
                break;
            }
        }
        validTwoDigits[0] = denomination;
        validTwoDigits[1] = count;
        return validTwoDigits;
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation"));
        ConsoleHelper.writeMessage("\t 1 - " + res.getString("operation.INFO"));
        ConsoleHelper.writeMessage("\t 2 - " + res.getString("operation.DEPOSIT"));
        ConsoleHelper.writeMessage("\t 3 - " + res.getString("operation.WITHDRAW"));
        ConsoleHelper.writeMessage("\t 4 - " + res.getString("operation.EXIT"));
        int number;
        Operation operation;
        while (true) {
            try {
                number = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(number);
                break;
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("choose.operation"));
            }
        }
        return operation;
    }

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }


}
