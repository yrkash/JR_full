package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;

    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    private Map<Integer, Integer> denominations = new TreeMap<>(Comparator.reverseOrder());

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

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int totalAmount = 0;
        //Временный Map для хранения denominations
        Map<Integer, Integer> temp = new TreeMap<>(Comparator.reverseOrder());
        temp.putAll(denominations);
        Map<Integer, Integer> resultDenominations = new TreeMap<>(Comparator.reverseOrder());
        while (true) {
            for (Map.Entry<Integer, Integer> entry: temp.entrySet()) {
                if (expectedAmount >= entry.getKey()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        if (totalAmount < expectedAmount) {
                            if (totalAmount == expectedAmount) break;
                            if (totalAmount + entry.getKey() <= expectedAmount) {
                                totalAmount += entry.getKey();
                                resultDenominations.merge(entry.getKey(), 1, Integer::sum);
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            if (totalAmount == expectedAmount) break;
            if (totalAmount < expectedAmount) {
                if (temp.size() > 0) {
                    Integer keyToRemove = temp.keySet().iterator().next();
                    temp.remove(keyToRemove);
                    totalAmount = 0;
                    resultDenominations.clear();
                } else {
                    throw new NotEnoughMoneyException();
                }
            }
        }
        for (Map.Entry<Integer, Integer> entry: resultDenominations.entrySet()) {
            Iterator<Integer> iterator = denominations.keySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(entry.getKey())) {
                    denominations.merge(entry.getKey(), -entry.getValue(), Integer::sum);
                }
            }
        }
        return resultDenominations;
    }
}
