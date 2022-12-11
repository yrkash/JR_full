package com.javarush.task.task37.task3703;

/*
Найти класс по описанию Ӏ Java Collections: 7 уровень, 6 лекция
*/


import java.util.concurrent.ConcurrentSkipListMap;

public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static <K, V> Class getExpectedClass() {

        return ConcurrentSkipListMap.class;
    }
}
