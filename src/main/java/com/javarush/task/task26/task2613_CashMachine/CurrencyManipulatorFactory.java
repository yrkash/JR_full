package com.javarush.task.task26.task2613_CashMachine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    static {
        CurrencyManipulator currencyManipulator = new CurrencyManipulator("USD");
        currencyManipulator.addAmount(500, 1);
        currencyManipulator.addAmount(200, 3);
        currencyManipulator.addAmount(50, 1);
        currencyManipulator.addAmount(10, 1);
        map.put("USD", currencyManipulator);
    }

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
