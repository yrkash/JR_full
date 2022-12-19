package com.javarush.task.task38.task3803;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
Runtime исключения (unchecked exception)
*/

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object i = Integer.valueOf(42);
        String s = (String)i;
    }

    public void methodThrowsNullPointerException() {
        Integer a = 1;
        a = null;
        System.out.println( a + 2);
    }

    public static void main(String[] args) {
        VeryComplexClass instance = new VeryComplexClass();
        instance.methodThrowsClassCastException();
    }
}
