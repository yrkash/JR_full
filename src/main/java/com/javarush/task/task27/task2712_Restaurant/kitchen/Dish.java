package com.javarush.task.task27.task2712_Restaurant.kitchen;

public enum Dish {
    FISH (25),
    STEAK (30),
    SOUP (15),
    JUICE (5),
    WATER (3);

    private int duration;

        public int getDuration() {
        return duration;
    }

    Dish(int duration) {
        this.duration = duration;
    }

    public static String allDishesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Dish dish: Dish.values()) {
            stringBuilder.append(dish.toString() +", ");
        }
        return stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length() - 1).toString();
    }
}
