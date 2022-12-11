package com.javarush.task.task22.task2207;

import java.io.*;
import java.util.*;

/*
Обращенные слова
*/

public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String path = reader.readLine();
        //String path = "c:/target/test.txt";
        reader.close();
        ArrayList<String> arrayList = new ArrayList<>();
        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] buffArray = line.split(" ");
                for (int i = 0; i < buffArray.length; i++) {
                    arrayList.add(buffArray[i]);
                }
            }
        }
        for (int i = 0; i < arrayList.size() - 1; i++) {
            String prevWord = arrayList.get(i);
            for (int j = i + 1; j < arrayList.size(); j++) {
                String curWord = arrayList.get(j);
                StringBuilder buff = new StringBuilder(curWord);
                String revCurWord = buff.reverse().toString();
                if ((prevWord.equals(revCurWord)) && !prevWord.equals("")) {
                    Pair pair = new Pair();
                    pair.first = prevWord;
                    pair.second = curWord;
                    result.add(pair);
                    arrayList.set(i,"");
                    arrayList.set(j,"");
                    break;
                }
            }
        }
        for (Pair pair: result) {
            System.out.println(pair);
        }
    }

    public static class Pair {
        String first;
        String second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return first == null && second == null ? "" :
                    first == null ? second :
                            second == null ? first :
                                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
