package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(2);
        solution.createExpression(3);
        solution.createExpression(4);
        solution.createExpression(5);
    }

    public void createExpression(int number) {
        int powCounter = 0;
        int remainder = 0;
        StringBuilder result = new StringBuilder(number + " =");
        do {
            remainder = number % 3;
            number = number / 3;
            if (remainder ==  1) result.append(" + " + (int)(Math.pow(3,  powCounter)));
            if (remainder == 2) {
                result.append(" - " + (int)(Math.pow(3,  powCounter)));
                number++;
            }
            powCounter++;
        } while (number > 0 );
        System.out.println(result);
    }
}