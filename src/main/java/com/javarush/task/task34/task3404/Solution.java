package com.javarush.task.task34.task3404;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Рекурсия для мат. выражения
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse("sin(2*(-5+1.5*4)+28)", 0); // Expected output: 0.5 6
    }

    public void recurse(final String expression, int countOperation) {

        String prepareStatement = prepareStatement(expression);
        Stack<Character> operation = new Stack<>();
        List<String> reversePolishNotation = new LinkedList<>();
        //implement
        System.out.println(prepareStatement(prepareStatement));
        makeRPN(prepareStatement);
    }

    public String makeRPN (String expression) {
        int countOfDigit = 0;
        int countOfNonDigit = 0;
        Stack<String> operation = new Stack<>();
        List<String> reversePolishNotation = new LinkedList<>();


        Pattern patternDigit = Pattern.compile("(\\d+)?(\\.\\d+)?");
        Pattern patternOther = Pattern.compile("\\D");

        /*Matcher matcherDigit = patternDigit.matcher(expression);
        while (matcherDigit.find()) {
            if (matcherDigit.group().length() > 0) countOfDigit++;
        }

        Matcher matcherOther = patternOther.matcher(expression);
        while (matcherOther.find()) {
            if (matcherOther.group().length() > 0 && !matcherOther.group().equals("."))
                countOfNonDigit++;
        }*/

        // Получаем список всех лексем из выражения для RPN
        List<String> expressionList = new ArrayList<>();
        StringBuilder expressionBuilder = new StringBuilder(expression);
        for (int i = 0; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i)) && expression.charAt(i) != '.') {
                expressionList.add(String.valueOf(expression.charAt(i)));
                expressionBuilder.deleteCharAt(0);
            }
            if (Character.isDigit(expression.charAt(i))) {
                Matcher matcherDigit = patternDigit.matcher(expressionBuilder.toString());
                if (matcherDigit.find()) {
                    expressionList.add(matcherDigit.group());
                    int currentDigitLength = matcherDigit.group().length();
                    expressionBuilder.delete(0, currentDigitLength);
                    if (currentDigitLength > 1) i += currentDigitLength - 1;
                }
            }
        }
        System.out.println(expressionList);

        return null;
    }
    public String prepareStatement (String expression) {
        //Заменяем все функции sin на одиночный аналог
        String workWith = expression.replaceAll("(S|s)(I|i)(N|n)", "s");
        //Заменяем все функции cos на одиночный аналог
        workWith = workWith.replaceAll("[Cc][Oo][Ss]", "c");
        //Заменяем все функции tan на одиночный аналог
        workWith = workWith.replaceAll("[Tt][Aa][Nn]", "t");
        // меняем "-" в начале выражения
        workWith = workWith.replaceAll("^\\-","0-");
        // меняем "+" в начале выражения
        workWith = workWith.replaceAll("^\\+","0+");
        // ищем унарные минусы, плюсы
        StringBuilder workWithBuilder = new StringBuilder(workWith);
        for (int i = 1; i < workWithBuilder.length(); i++) {
            char prevChar = workWithBuilder.charAt(i - 1);
            char currentChar = workWithBuilder.charAt(i);
            if (currentChar == '-' && !Character.isDigit(prevChar) && prevChar != ')') {
                workWithBuilder.setCharAt(i, '@');
            }
            if (currentChar == '+' && !Character.isDigit(prevChar) && prevChar != ')') {
                workWithBuilder.setCharAt(i, '%');
            }
        }
        workWith = workWithBuilder.toString().replaceAll("@","0-");
        workWith = workWith.replaceAll("%","0+");
        //убираем все пробелы
        workWith = workWith.replaceAll(" ","");
        return workWith;
    }

    public double makeFunction(String s, double first) {
        switch (s) {
            case "s": {
                return Math.sin(Math.toRadians(first));
            }
            case "c": {
                return Math.cos(Math.toRadians(first));
            }
            case "t": {
                return Math.tan(Math.toRadians(first));
            }

            default:
                return -1;
        }
    }

    public int getPriority(String s) {
        switch (s) {
            case "+":
            case "-":
                 return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            case "c":
            case "s":
            case "t":
                return 4;
            default:
                return -1;
        }
    }

    public Solution() {
        //don't delete
    }
}
