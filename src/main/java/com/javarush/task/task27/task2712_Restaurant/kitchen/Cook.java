package com.javarush.task.task27.task2712_Restaurant.kitchen;

import com.javarush.task.task27.task2712_Restaurant.ConsoleHelper;
import com.javarush.task.task27.task2712_Restaurant.Tablet;
import com.javarush.task.task27.task2712_Restaurant.statistic.StatisticManager;
import com.javarush.task.task27.task2712_Restaurant.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer {
    private final String name;

    public Cook(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public void update(Observable o, Object arg) {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Tablet tablet = (Tablet) o;
        Order order = (Order) arg;
        ConsoleHelper.writeMessage("Start cooking - " + arg);
        this.setChanged();
        this.notifyObservers(order);
        CookedOrderEventDataRow cookedOrderEventDataRow = new CookedOrderEventDataRow(tablet.toString(),name, order.getTotalCookingTime() * 60,order.getDishes());
        statisticManager.register(cookedOrderEventDataRow);

    }
}
