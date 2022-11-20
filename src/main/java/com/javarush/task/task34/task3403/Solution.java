package com.javarush.task.task34.task3403;

/*
Разложение на множители с помощью рекурсии
*/

public class Solution {
    public void recurse(int n) {

        if (n <= 1) return;

        if (minDivider(n) == n) {
            System.out.print(n);
        } else {
            int currentDivider = minDivider(n);
            System.out.print(currentDivider + " ");
            recurse(n / currentDivider);
        }
    }

    public int minDivider (int n) {
        int divider = n;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                divider = i;
                break;
            }
        }
        return divider;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse(132);
    }
}
