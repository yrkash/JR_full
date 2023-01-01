package com.javarush.task.task26.task2613;

import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        denominations.merge(denomination, count, Integer::sum);
    }
    public int getTotalAmount() {
        int totalAmount = 0;
        for (Map.Entry<Integer,Integer> current: denominations.entrySet()) {
            totalAmount += current.getKey() * current.getValue();
        }
        return totalAmount;
    }

    public boolean hasMoney() {
        return getTotalAmount() != 0;
    }

}
