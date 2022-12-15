package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/temp/"));

        logParser.execute("get date for event = \"SOLVE_TASK\"");
        System.out.println(logParser.execute("get date for event = \"SOLVE_TASK\""));
    }
}