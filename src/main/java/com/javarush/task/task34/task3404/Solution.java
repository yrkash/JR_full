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


        solution.recurse("tan(45)", 0);  System.out.println("1 1 - expected output");
        solution.recurse("tan(-45)", 0);  System.out.println("-1 2 - expected output");
        solution.recurse("0.305", 0);  System.out.println("0.3 0 - expected output");
        solution.recurse("0.3051", 0);  System.out.println("0.31 - expected output");
        solution.recurse("(0.3051)", 0);  System.out.println("0.31 - expected output");
        solution.recurse("1+(1+(1+1)*(1+1))*(1+1)+1", 0);  System.out.println("12 8 - expected output");
        solution.recurse("tan(44+sin(89-cos(180)^2))", 0);  System.out.println("1 6 - expected output");
        solution.recurse("-2+(-2+(-2)-2*(2+2))", 0);  System.out.println("-14 8 - expected output");
        solution.recurse("sin(80+(2+(1+1))*(1+1)+2)", 0);  System.out.println("1 7 - expected output");
        solution.recurse("1+4/2/2+2^2+2*2-2^(2-1+1)", 0);  System.out.println("6 11 - expected output");
        solution.recurse("10-2^(2-1+1)", 0);  System.out.println("6 4 - expected output");
        solution.recurse("2^10+2^(5+5)", 0);  System.out.println("2048 4 - expected output");
        solution.recurse("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", 0);  System.out.println("72.96 8 - expected output");
        solution.recurse("0.000025+0.000012", 0);  System.out.println("0 1 - expected output");
        solution.recurse("cos(3 + 19*3)", 0);  System.out.println("0.5 3 - expected output");


    }

    public void recurse(final String expression, int countOperation) {

        if (expression.matches("^(-)?(\\d+)(\\.\\d+)?$")) {
            try {
                System.out.println(resultToString(Double.parseDouble(expression))+ " " + countOperation);
                return;
            } catch (NumberFormatException ignore) {

            }

        } else {
            if (countOperation == 0) {
                try {
                    Double result = Double.parseDouble(expression);
                    String stringWeNeed = resultToString(result);
                    System.out.println(stringWeNeed + " " + countOperation);
                    return;
                } catch (NumberFormatException ignore) {
                    //ignore
                }
                String prepareStatement = prepareStatement(expression);
                List<String> rpn = makeRPN(prepareStatement);
                String currentResult = makeOperation(rpn);
                countOperation++;
                recurse(currentResult, countOperation);
            }  else {
                String[] tokens = expression.split(" ");
                List <String> rpn = new LinkedList<>();
                for (String token : tokens) {
                    rpn.add(token);
                }
                String currentResult = makeOperation(rpn);
                countOperation++;

                recurse(currentResult, countOperation);
            }
        }
    }

    public String makeOperation(List<String> rpn) {

        Set<String> binaryOperationSet = new HashSet<>(Arrays.asList("+","-","*","/","^"));
        Set<String> unaryOperationSet = new HashSet<>(Arrays.asList("s","c","t"));

        List<String> buffList = new LinkedList<>();
        boolean hasMakingCalcInThisStep = false;

        for (int i = 0; i < rpn.size(); i++) {
            if (unaryOperationSet.contains(rpn.get(i)) && !hasMakingCalcInThisStep) {
                hasMakingCalcInThisStep = true;

                double currentCalc = makeFunction(rpn.get(i), Double.parseDouble(rpn.get(i - 1)));
                buffList.remove(buffList.size() - 1);
                buffList.add(resultToString(currentCalc));
            } else {
                if (binaryOperationSet.contains(rpn.get(i)) && !hasMakingCalcInThisStep) {
                    hasMakingCalcInThisStep = true;
                    String firstOperand = rpn.get(i - 2);
                    String secondOperand = rpn.get(i - 1);
                    String operation = rpn.get(i);
                    double currentCalc = 0;
                    switch (operation) {
                        case "+":
                            currentCalc =  Double.parseDouble(firstOperand) + Double.parseDouble(secondOperand);
                            break;
                        case "-":
                            currentCalc =  Double.parseDouble(firstOperand) - Double.parseDouble(secondOperand);
                            break;
                        case "*":
                            currentCalc =  Double.parseDouble(firstOperand) * Double.parseDouble(secondOperand);
                            break;
                        case "/":
                            currentCalc =  Double.parseDouble(firstOperand) / Double.parseDouble(secondOperand);
                            break;
                        case "^":
                            currentCalc =  Math.pow(Double.parseDouble(firstOperand), Double.parseDouble(secondOperand));
                            break;
                        default:
                            currentCalc = 0.0;
                            break;
                    }
                    buffList.remove(buffList.size() - 2);
                    buffList.remove(buffList.size() - 1);
                    buffList.add(resultToString(currentCalc));
                } else {
                    buffList.add(rpn.get(i));
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (String token: buffList) {
            result.append(token + " ");
        }
        return result.toString().trim();
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
        //убираем все пробелы
        String workWith = expression.replaceAll(" ","");
        //Заменяем все функции sin на одиночный аналог
        workWith = workWith.replaceAll("(S|s)(I|i)(N|n)", "s");
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
