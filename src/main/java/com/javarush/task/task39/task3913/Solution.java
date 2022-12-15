package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/temp/"));

        System.out.println(logParser.getNumberOfAttemptToSolveTask(18, null, null));
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber( null, null));



        System.out.println(logParser.getDatesForUserAndEvent("Amigo", Event.SOLVE_TASK, null, new Date()));
        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));
        System.out.println(logParser.getDatesWhenErrorHappened(null, null));
        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Amigo", null, null));
        System.out.println(logParser.getDateWhenUserLoggedFirstTime("fdf", null, null));
        System.out.println(logParser.getDateWhenUserSolvedTask("Vasya Pupkin", 1, null, null));
        System.out.println(logParser.getDateWhenUserDoneTask("Eduard Petrovich Morozko", 48, null, null));
        System.out.println(logParser.getDatesWhenUserWroteMessage("Vasya Pupkin", null, null));
        System.out.println(logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null));
    }
}