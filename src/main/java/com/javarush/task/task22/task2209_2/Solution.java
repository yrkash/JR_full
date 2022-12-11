package com.javarush.task.task22.task2209_2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
Составить цепочку слов
*/

public class Solution {

    static class Mark {
        public Mark(int pre, int post) {
            this.pre  = pre;
            this.post = post;
        }
        public int pre;
        public int post;
    };

    static Map<String, Integer> visitedMap = new LinkedHashMap<String, Integer>();
    static int counter = 0;
    static ArrayList<String> cityList = new ArrayList<>();


    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //String path = reader.readLine();
        String path = "c:/temp/test.txt";
        reader.close();
        ArrayList<String> cityList = new ArrayList<>();
        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                while (st.hasMoreTokens()) {
                    String city = st.nextToken();
                    //countOfCity++;
                    cityList.add(city);
                }
            }
        }
        //...
        StringBuilder result = getLine();
        //System.out.println(result.toString());
        Map<String,List<String>> vertexMap = getVertexMap(cityList);
        for (Map.Entry<String, List<String>> entry : vertexMap.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }

        for (String v : cityList) {
            dfs(v, vertexMap);
        }

        for (Map.Entry<String, Integer> entry : visitedMap.entrySet()) {
            System.out.format("%1$s : %2$d\n", entry.getKey(), entry.getValue());
        }

    }

    public static StringBuilder getLine(String... words) {
        return null;
    }

    public static Map<String,List<String>> getVertexMap (ArrayList<String> cityList) {
        ArrayList<String> copyList = new ArrayList<>(cityList);
        Map<String,List<String>> vertexMap = new LinkedHashMap<>();
        for (String city: cityList) {
            List<String> adjacentCity = new ArrayList<>();
            char curLastLetter = city.charAt(city.length() - 1);
            copyList.remove(city);
            for (String cityTo: copyList) {
                if (cityTo.toLowerCase().charAt(0) == curLastLetter) {
                    adjacentCity.add(cityTo);
                }
            }
            vertexMap.put(city, adjacentCity);
            copyList = new ArrayList<>(cityList);
            Solution.cityList = cityList;
        }
        return vertexMap;
    }

    static void dfs(String city, Map<String, List<String>> vm) {
        if (visitedMap.size() == 0) {
            visitedMap.put(city, counter);
        } else {
            if (visitedMap.containsKey(city)) return;
        }
        boolean isNewCity = false;

        //Map<String, List<String>> vm = getVertexMap(cityList);
        List<String> adjacentCity = vm.get(city);
        if ((visitedMap.size() < cityList.size() - 1) && (adjacentCity.size() == 0)) {
            if (visitedMap.containsKey(city)) visitedMap.values().removeIf(value -> value == counter);
            return;
        }
        for (String potCity: adjacentCity) {
            if (!visitedMap.containsKey(potCity)) isNewCity = true;
        }
        if ((isNewCity) || (visitedMap.size() == cityList.size() - 1)) {
            counter++;
            visitedMap.put(city, counter);
        } else {
            //visitedMap.clear();
            //visitedMap.remove(counter);
            visitedMap.values().removeIf(value -> value == counter);
            counter--;
            return;
        }
        for (String v : adjacentCity) {
            if (visitedMap.containsKey(v)) continue;
            dfs(v, vm);
        }
    }

}
