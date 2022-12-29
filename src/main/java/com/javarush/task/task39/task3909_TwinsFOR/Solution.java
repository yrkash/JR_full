package com.javarush.task.task39.task3909_TwinsFOR;

/*
Одно изменение
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("laTTar", "letter"));
    }

    public static boolean isOneEditAway(String first, String second) {
        int differences = 0;
        for (int f = 0, s = 0; f < first.length() && s < second.length(); f++, s++) {
            if (first.toUpperCase().charAt(f) != second.toUpperCase().charAt(s)) {
                differences++;
                if (first.length() < second.length()) f--;
                if (first.length() > second.length()) s--;
            }
        }
        return differences < 2;
    }
}
