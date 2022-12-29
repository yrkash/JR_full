package com.javarush.task.task39.task3908;

/*
Возможен ли палиндром?
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation("abcba"));
    }

    public static boolean isPalindromePermutation(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        List<Character> buf = new ArrayList<>();
        Iterator<Character> iterator = buf.iterator();
        for (int i = 0; i < chars.length; i++) {
            if (buf.contains(chars[i])) {
                buf.remove((Character) chars[i]);
            } else {
                buf.add(chars[i]);
            }
        }
        return (buf.size() <= 1) ? true : false;
    }
}
