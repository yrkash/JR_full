package com.javarush.task.task27.task2712_Restaurant;

import com.javarush.task.task27.task2712_Restaurant.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String result = new String();
        while (true) {
            try {
                result = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
        return result;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        ConsoleHelper.writeMessage("choose a dish");
        String insertDish;
        while (!(insertDish = reader.readLine()).equals("exit")) {
            try {
                dishes.add(Dish.valueOf(insertDish));
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage("choose a right dish");
            }
        }
        return dishes;
    }



}
