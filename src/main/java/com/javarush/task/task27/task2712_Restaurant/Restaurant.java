package com.javarush.task.task27.task2712_Restaurant;

import com.javarush.task.task27.task2712_Restaurant.kitchen.Cook;
import com.javarush.task.task27.task2712_Restaurant.kitchen.Waiter;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    public static void main(String[] args) {
        Tablet tablet = new Tablet(5);
        Cook cook = new Cook("Irina");
        Waiter waiter = new Waiter();
        tablet.addObserver(cook);
        cook.addObserver(waiter);

        tablet.createOrder();
        tablet.createOrder();
        tablet.createOrder();
        tablet.createOrder();


        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();


        /*
        DirectorTabletTest directorTabletTest = new DirectorTabletTest();
        directorTabletTest.setUp();
        directorTabletTest.printAdvertisementProfit();


         */


    }

}
