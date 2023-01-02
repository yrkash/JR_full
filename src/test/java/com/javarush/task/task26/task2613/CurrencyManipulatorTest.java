package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;
import com.javarush.task.task33.task3320_JUnit.Sex;
import com.javarush.task.task33.task3320_JUnit.User;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

public class CurrencyManipulatorTest extends TestCase {

    private CurrencyManipulator currencyManipulator = new CurrencyManipulator("USD");
    private Map<String, CurrencyManipulator> map = new HashMap<>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        currencyManipulator.addAmount(500, 1);
        currencyManipulator.addAmount(200, 3);
        currencyManipulator.addAmount(50, 1);
        currencyManipulator.addAmount(10, 1);
        map.put("USD", currencyManipulator);
    }


    @Test
    public void testWithdrawAmount700() throws NotEnoughMoneyException {
        Map<Integer, Integer> expected = new TreeMap<>(Comparator.reverseOrder());
        expected.put(500,1);
        expected.put(200,1);
        Map<Integer,Integer> actual = currencyManipulator.withdrawAmount(700);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testWithdrawAmount600() throws NotEnoughMoneyException {
        Map<Integer, Integer> expected = new TreeMap<>(Comparator.reverseOrder());
        expected.put(200,3);
        Map<Integer,Integer> actual = currencyManipulator.withdrawAmount(600);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testWithdrawAmount1000() throws NotEnoughMoneyException {
        Map<Integer, Integer> expected = new TreeMap<>(Comparator.reverseOrder());
        expected.put(200,3);
        Map<Integer,Integer> actual = currencyManipulator.withdrawAmount(600);
    }

}