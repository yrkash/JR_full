package com.javarush.task.task25.task2512;

import java.util.LinkedList;
import java.util.List;

/*
Живем своим умом
*/

public class Solution implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.interrupt();
        Throwable cause = e.getCause();
        //System.out.println(e.getMessage());
        if (cause != null) {
            uncaughtException(t, cause);
        }
        System.out.println(e.toString());
    }

    public static void main(String[] args) throws Exception {

        new Solution().uncaughtException(new Thread(), new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI"))));
    }
}
