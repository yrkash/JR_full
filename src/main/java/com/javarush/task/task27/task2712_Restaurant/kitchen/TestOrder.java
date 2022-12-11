package com.javarush.task.task27.task2712_Restaurant.kitchen;

import com.javarush.task.task27.task2712_Restaurant.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
        initDishes();
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<>();
        int randomCountOfDishes = (int) (Math.random() * 5);
        for (int i = 0; i < randomCountOfDishes; i++) {
            dishes.add(Dish.values()[(int) (Math.random() * 4)]);
        }
    }
}
