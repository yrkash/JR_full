package com.javarush.task.task27.task2712_Restaurant.kitchen;

import com.javarush.task.task27.task2712_Restaurant.ConsoleHelper;
import com.javarush.task.task27.task2712_Restaurant.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() throws IOException {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        return dishes.stream().mapToInt(Dish::getDuration).sum();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) return "";
        return String.format("Your order: %s of %s cooking time %smin" , dishes, tablet.toString(), getTotalCookingTime());
        //return "Your order: " + dishes + " of " + tablet.toString() +", cooking time ";
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }
}
