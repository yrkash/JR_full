package com.javarush.task.task35.task3512_Generator;

/*
Генератор объектов
*/

public class Solution {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Generator<Event> eventGenerator = new Generator<>(Event.class);

        System.out.println(eventGenerator.newInstance().getId());
        System.out.println(eventGenerator.newInstance().getId());
        System.out.println(eventGenerator.newInstance().getId());
    }

}
