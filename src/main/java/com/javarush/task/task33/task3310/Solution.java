package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        testStrategy(new OurHashBiMapStorageStrategy(), 1000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        return strings.stream()
                .map(str-> shortener.getId(str))
                .collect(Collectors.toSet());
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        return keys.stream()
                .map(k-> shortener.getString(k))
                .collect(Collectors.toSet());
    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> testSet = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            testSet.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date dateBefore = new Date();
        Set<Long> testGetIds = getIds(shortener, testSet);
        Date dateAfter = new Date();
        Long methodGetIdsTime = dateAfter.getTime() - dateBefore.getTime();
        Helper.printMessage("Время работы метода getIds - " + methodGetIdsTime);

        dateBefore = new Date();
        Set<String> testGetStrings = getStrings(shortener, testGetIds);
        dateAfter = new Date();
        Long methodGetStringTime = dateAfter.getTime() - dateBefore.getTime();
        Helper.printMessage("Время работы метода getStrings - " + methodGetStringTime);
        boolean testResult = testSet.equals(testGetStrings);
        if (testResult) System.out.println("Тест пройден.");
        else {
            System.out.println("Тест не пройден.");
        }
    }

}
