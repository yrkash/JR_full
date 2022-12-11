package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
Составить цепочку слов
*/

public class Solution {

    public static ArrayList<String> arrayList = new ArrayList<>();
    public static TreeMap<Character, Integer> letters = new TreeMap<>();
    public static ArrayList<Character> potentialStartLetters = new ArrayList<>();
    public static int countOfCity = 0;
    public static void main(String[] args) throws IOException {

        //...Киев Нью-Йорк Амстердам Вена Мельбурн

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //String path = reader.readLine();
        String path = "c:/temp/test.txt";
        reader.close();

        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                while (st.hasMoreTokens()) {
                    String city = st.nextToken();
                    countOfCity++;
                    arrayList.add(city);
                }
            }
        }
        System.out.println(arrayList);
        //...
        StringBuilder result = getLine(arrayList.toArray(new String[arrayList.size()]));
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        if (words == null) return null;
        ArrayList<String> copyList = new ArrayList<>(arrayList);
        ArrayList<String> buffList = new ArrayList<>();

        System.out.println(countOfCity);
        StringBuilder wordLine = new StringBuilder();

        int countOfWords = 0;
        char curLastLetter = ' ';
        boolean notFull = false;
        int indexOfFirst = 0;

        while (indexOfFirst < countOfCity - 1) {
            String firstName = copyList.get(indexOfFirst);
            buffList.add(firstName);
            countOfWords = 1;
            copyList.remove(indexOfFirst);
            String [] var = findNext(firstName, copyList);
            for (int i = 0; i < countOfCity - 1; i++) {

            }
        }


        while (true) {
            //wordLine.append(copyList.get(indexOfFirst));
            //System.out.println(wordLine);

            countOfWords = 1;
            curLastLetter = copyList.get(indexOfFirst).charAt(copyList.get(indexOfFirst).length() - 1);

            for (int i = 0; i < countOfCity; i++) {
                notFull = true;
                int j = 0;
                while (j < copyList.size())
                {
                    String testedWord = copyList.get(j);
                    if (testedWord.toLowerCase().charAt(0) == curLastLetter) {
                        wordLine.append(" " + copyList.get(j));

                        curLastLetter = testedWord.charAt(testedWord.length() - 1);
                        copyList.remove(j);
                        countOfWords++;
                        System.out.println(countOfWords + " " + wordLine);
                        notFull = false;
                        j = 0;
                        break;
                    } else {
                        j++;
                    }
                }
                if ((notFull) && (countOfWords < countOfCity)) {
                    wordLine = new StringBuilder();
                    copyList = new ArrayList<>(arrayList);
                    notFull = false;
                    countOfWords = 0;
                    break;
                }
            }
            indexOfFirst++;
            if (countOfWords == countOfCity) break;
        }
        return wordLine;
    }

    public static String[] findNext (String word, ArrayList<String> list) {

        char curLastLetter = word.charAt(word.length() - 1);
        ArrayList <String> buffList = new ArrayList<>();
        for (String words: list) {
            char nextFirstLetter = words.toLowerCase().charAt(0);
            if (curLastLetter == nextFirstLetter) {
                buffList.add(words);
            }
        }
        return buffList.toArray(new String[buffList.size()]);
    }
}
