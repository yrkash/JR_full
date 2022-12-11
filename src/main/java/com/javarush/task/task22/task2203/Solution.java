    package com.javarush.task.task22.task2203;

    /*
    Найти подстроку
    */

    public class Solution {
        public static void main(String[] args) throws Exception {
                //System.out.println(getPartOfString("\tJavaRush - лучший сервис \tобучения Java\t."));
            System.out.println(getPartOfString("\tJavaRush - лучший сервис \tобучения Java\t."));


        }

        public static String getPartOfString(String string) throws TooShortStringException {
            if (string == null) {
                throw new TooShortStringException();
            }
            String[] parts = string.split("\t");
            if ((string == null) || (parts.length < 3)) throw new TooShortStringException();
            return parts[1];
        }

        public static class TooShortStringException extends Exception {

        }
    }
