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
//        solution.recurse("2.5432 + 1", 0);
        solution.recurse("sin(2*(-5+1.5*4)+28)", 0); // Expected output: 0.5 6
//        solution.recurse("(6+10-4)/(1+1*2)+1", 0); // Expected output: 0.5 6
//        solution.recurse("-cos(180)^2", 0); // Expected output: 0.5 6

        /*
        Double d = new Double(expression);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        DecimalFormat df = (DecimalFormat) numberFormat;
        df.applyPattern("#.##");
        String stringWeNeed = df.format(d);
        */

    }

    public void recurse(final String expression, int countOperation) {
        Set<String> binaryOperationSet =
                new HashSet<>(Arrays.asList("+","-","*","/","^"));
        Set<String> unaryOperationSet = new HashSet<>(Arrays.asList("s","c","t"));
        if (countOperation == 0) {
            try {
                Double result = Double.parseDouble(expression);
                String stringWeNeed = resultToString(result);
                System.out.println(stringWeNeed + " " + countOperation);
                return;
            } catch (NumberFormatException e) {}

            String prepareStatement = prepareStatement(expression);
            List<String> rpn = makeRPN(prepareStatement);
            System.out.println(rpn);
            boolean hasMakingCalcInThisStep = false;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < rpn.size() - 1; i++) {
                if (unaryOperationSet.contains(rpn.get(i + 1)) && !hasMakingCalcInThisStep) {
                    hasMakingCalcInThisStep = true;
                    double currentCalc = makeFunction(rpn.get(i + 1), Double.parseDouble(rpn.get(i)));
                    i++;
                    builder.append(resultToString(currentCalc) + " ");
                } else {
                    if (binaryOperationSet.contains(rpn.get(i + 1)) && !hasMakingCalcInThisStep) {
                        hasMakingCalcInThisStep = true;
                        StringBuilder temp = new StringBuilder(builder);
                        String firstOperand = temp.reverse().toString().trim().split(" ")[0];
                        String secondOperand = rpn.get(i);
                        String operation = rpn.get(i + 1);
                        double currentCalc =  Double.parseDouble(firstOperand) - Double.parseDouble(secondOperand);
                        i++;
                        builder.delete(builder.length() - firstOperand.length() - 1, builder.length());
                        builder.append(currentCalc + " ");
                    } else {
                        builder.append(rpn.get(i) + " ");
                    }
                }
            }
            if (builder.toString().matches("[+\\-*/^cst]+")) builder.append(rpn.get(rpn.size() - 1));
            System.out.println(builder);

        }


        Stack<Character> operation = new Stack<>();
        List<String> reversePolishNotation = new LinkedList<>();
        //implement
    }

    public String resultToString (double result) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        DecimalFormat df = (DecimalFormat) numberFormat;
        df.applyPattern("#.##");
        return df.format(result);
    }

    public List<String> makeRPN (String expression) {
        Set<String> operationSet =
                new HashSet<>(Arrays.asList("+","-","*","/","^","s","c","t"));
        Stack<String> operations = new Stack<>();
        List<String> reversePolishNotation = new LinkedList<>();

        /*
        Matcher matcherOther = patternOther.matcher(expression);
        while (matcherOther.find()) {
            if (matcherOther.group().length() > 0 && !matcherOther.group().equals("."))
                countOfNonDigit++;
        }*/

        // Получаем список всех лексем из выражения для RPN
        List<String> tokenList = new ArrayList<>();
        StringBuilder expressionBuilder = new StringBuilder(expression);
        for (int i = 0; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i)) && expression.charAt(i) != '.') {
                tokenList.add(String.valueOf(expression.charAt(i)));
                expressionBuilder.deleteCharAt(0);
            }
            if (Character.isDigit(expression.charAt(i))) {
                Pattern patternDigit = Pattern.compile("(\\d+)?(\\.\\d+)?");
                Matcher matcherDigit = patternDigit.matcher(expressionBuilder.toString());
                if (matcherDigit.find()) {
                    tokenList.add(matcherDigit.group());
                    int currentDigitLength = matcherDigit.group().length();
                    expressionBuilder.delete(0, currentDigitLength);
                    if (currentDigitLength > 1) i += currentDigitLength - 1;
                }
            }
        }
//        System.out.println(tokenList);

        // Готовим обратную польскую последовтельность
        for (String token: tokenList) {
            if (operationSet.contains(token)) {
                if (operations.empty()) {
                    operations.push(token);
                } else {
                    String prevToken = operations.peek();
                    if (getPriority(prevToken) >= getPriority(token)) {
                        operations.pop();
                        reversePolishNotation.add(prevToken);
                        operations.push(token);
                    } else {
                        operations.push(token);
                    }
                }
                continue;
            }
            if (token.matches("(\\d+)?(\\.\\d+)?")) {
                reversePolishNotation.add(token);
                continue;
            }
            if (token.equals("(")) {
                operations.push(token);
                continue;
            }
            if (token.equals(")")) {
                String prevToken = operations.peek();
                // если сразу перед ")" в стеке "("
                if (prevToken.equals("(")) {
                    operations.pop();
                    continue;
                }
                while (!prevToken.equals("(")) {
                    prevToken = operations.pop();
                    if (!prevToken.equals("(")) reversePolishNotation.add(prevToken);
                }
            }
        }
        while (!operations.empty()) {
            String token = operations.pop();
            if (!token.equals("(")) reversePolishNotation.add(token);
        }
        return reversePolishNotation;
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
