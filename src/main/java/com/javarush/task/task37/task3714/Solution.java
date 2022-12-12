package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
Древний Рим
*/

public class Solution {

    private static Map<String, Integer> romanDecMap = new HashMap<>();
    static {
        romanDecMap.put("I", 1);
        romanDecMap.put("V", 5);
        romanDecMap.put("X", 10);
        romanDecMap.put("L", 50);
        romanDecMap.put("C", 100);
        romanDecMap.put("D", 500);
        romanDecMap.put("M", 1000);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {

        int result = 0;
        String[] chars = s.split("");
        result += romanDecMap.get(chars[chars.length - 1]);
        int prevValue = result;
        if (chars.length >= 2) {
            for (int i = chars.length - 2; i >= 0 ; i--) {
                int curValue = romanDecMap.get(chars[i]);
                if (curValue < prevValue) {
                    result -= curValue;
                } else {
                    result += curValue;
                }
                prevValue = curValue;
            }
        }
        return result;
    }
}
