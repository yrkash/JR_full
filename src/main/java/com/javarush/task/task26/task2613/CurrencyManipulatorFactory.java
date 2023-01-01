package com.javarush.task.task26.task2613;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        String curCurrencyCode = currencyCode.toUpperCase();
        if (!map.containsKey(curCurrencyCode)) {
            CurrencyManipulator currencyManipulator = new CurrencyManipulator(curCurrencyCode);
            map.put(curCurrencyCode,currencyManipulator);
            return currencyManipulator;
        } else {
            return map.get(curCurrencyCode);
        }
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }

    private CurrencyManipulatorFactory() { }
}
