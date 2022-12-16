package com.javarush.task.task39.task3913_LogParser_StreamAPI_reflection;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/temp/"));

//        logParser.execute("get date for event = \"SOLVE_TASK\"");
        logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"");

        System.out.println(logParser.execute("get date for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));

    }
}