package com.javarush.task.task36.task3601;

import java.util.ArrayList;
import java.util.List;

/*
MVC - простая версия
*/

public class Solution {

    public View view = new View();
    public static void main(String[] args) {

        new Solution().view.fireShowDataEvent();
    }

}
