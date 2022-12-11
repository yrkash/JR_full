package com.javarush.task.task22.task2212;

/*
+380501234567 - true
+38(050)1234567 - true
(050)1234567 - true
0(501)234567 - true
+38)050(1234567 - false
+38(050)123-45-67 - false
050ххх4567 - false
050123456 - false
(0)501234567 - false

*/

public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if (telNumber == null) return false;
        int length = telNumber.length();

        if ((length < 10) || (length > 15) || (length == 11) || (length == 14)) return false;
        Boolean test1 = telNumber.matches("^\\+\\d{1,12}$");
        Boolean test2 = telNumber.matches("^\\+\\d{0,9}\\(\\d{3}\\)\\d{1,9}$");
        Boolean test3 = telNumber.matches("^\\(\\d{3}\\)\\d{7}$");
        Boolean test4 = telNumber.matches("^\\d{1,6}\\(\\d{3}\\)\\d{1,6}$");


        //return test1 || test2 || test3 || test4;
        return (telNumber.matches("^\\+(\\d[()]?){12}$") || telNumber.matches("^([()]?\\d){10}$"))
                && telNumber.matches("^(\\+)?(\\d+)?(\\(\\d{3}\\))?\\d+$");
    }


 public static void main(String[] args) {
        String test =
                "+7(499)80501234 - true\n" +
                        "+237(499)501234 - true\n" +
                        "(050)1234567 - true\n" +
                        "0(501)234567 - true\n" +
                        "+38)050(1234567 - false\n" +
                        "+38(050)123-45-67 - false\n" +
                        "050ххх4567 - false\n" +
                        "050123456 - false\n" +
                        "050123456 - false\n" +
                        "(0)501234567 - false";
        String[] words;
        boolean check;
        for (String s : test.split("\\n")) {
            words = s.split("\\s");
            check = checkTelNumber(words[0]);
            System.out.printf("%-30s\t%b\t%s%n", s, check, check == "true".equals(words[2]) ? "" : "!!!");
        }

    }
}
