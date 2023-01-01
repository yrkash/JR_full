package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String result = new String();
        while (true) {
            try {
                result = bis.readLine();
                break;
            } catch (IOException ignore) {
            }
        }
        return result;
    }

    public static String askCurrencyCode() {
        writeMessage("Please insert currencyCode (only 3 symbols)");
        String result = new String();
        while (true) {
            result = readString();
            if (result.length() != 3) {
                writeMessage("Please insert currencyCode (only 3 symbols)");
            } else {
                break;
            }
        }
        return result.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        writeMessage("Please insert denomination and count");
        String denomination;
        String count;
        String result;
        String[] validTwoDigits = new String[2];
        while (true) {
            result = readString();
            if (!result.matches("^[1-9]\\d*\\s[1-9]\\d*")) {
                writeMessage("Please insert denomination and count");
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

    public static Operation askOperation() {
        writeMessage("Please insert number of Operation (1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT)");
        int number;
        Operation operation;
        while (true) {
            try {
                number = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(number);
                break;
            } catch (IllegalArgumentException e) {
                writeMessage("Please insert number of Operation (1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT)");
            }
        }
        return operation;
    }


}
