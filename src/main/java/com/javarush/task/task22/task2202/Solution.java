package com.javarush.task.task22.task2202;

/*
Найти подстроку
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getPartOfString("JavaRush - лучший сервис обучения Java."));
        //System.out.println(getPartOfString("JavaRush-лучшийсервисобучения Java."));
    }

    public static String getPartOfString(String string) {
        try {
            String form = "%s %s %s %s";
            String[] parts = string.split(" ");
            return String.format(form, parts[1], parts[2], parts[3], parts[4]);
        } catch (RuntimeException e) {
            throw new TooShortStringException();
        }
    }

    public static class TooShortStringException extends RuntimeException {

    }
}
