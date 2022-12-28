package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
Уникальные подстроки
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        HashSet<Character> set = new HashSet<>();
        int maxLength = 0;
        int curLength = 0;
        for (int i = 0; i < s.length(); i++) {
            int sizeBefore = set.size();
            Character curChar = s.charAt(i);
            set.add(curChar);
            int sizeAfter = set.size();
            if (sizeBefore == sizeAfter) {
                set.clear();
                set.add(curChar);
                curLength = 1;
            } else {
                curLength++;
            }
            if (maxLength < curLength) maxLength = curLength;
        }
        return maxLength;
    }
}
