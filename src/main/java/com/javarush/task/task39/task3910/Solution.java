package com.javarush.task.task39.task3910;

/*
isPowerOfThree
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(isPowerOfThree(243));
    }

    public static boolean isPowerOfThree(int n) {
        if (n < 1) return false;
        if (n == 1) return true;
        if (n / 3 == 1 && n % 3 == 0) {
            if (n % 3 == 0) {
                return true;
            }
            return false;
        } else {
            if (n / 3 != 1 && n % 3 == 0) {
                return isPowerOfThree( n / 3);
            } else {
                return false;
            }
        }
    }
}
