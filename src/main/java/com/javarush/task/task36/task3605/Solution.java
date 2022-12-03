package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/*
Использование TreeSet
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        TreeSet<Character> treeSet = new TreeSet<>();
        String str;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))){

            while ((str = bufferedReader.readLine()) != null) {
                String modifiedLine = str.replaceAll("\\W","").toLowerCase();
                for (int i = 0; i < modifiedLine.length(); i++) {
                    treeSet.add(modifiedLine.charAt(i));
                }
            }
        }
        treeSet.stream()
                .limit(5)
                .forEach(e->System.out.print(e));
    }
}
