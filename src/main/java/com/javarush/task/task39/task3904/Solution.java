package com.javarush.task.task39.task3904;

import java.util.Arrays;

/*
Лестница
*/

public class Solution {
    private static int n = 70;

    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        if (n < 0) return 0L;
        if (n == 0) return 1L;
        long[] a = new long[n + 1];
        a[1] = 1;
        a[2] = 2;
        a[3] = 4;
        int i = 4;
        while(i <= n){
            a[i] = a[i - 1] + a[i - 2] + a[i - 3];
            i++;
        }
        return a[n];
    }
}

