package com.javarush.task.task27.task2712_Restaurant;


import com.javarush.task.task27.task2712_Restaurant.ad.AdvertisementManager;
import com.javarush.task.task27.task2712_Restaurant.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712_Restaurant.kitchen.Order;
import com.javarush.task.task27.task2712_Restaurant.kitchen.TestOrder;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {

    private final int number;

    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        try {
            Order order = new Order(this);
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            try {
                advertisementManager.processVideos();
            } catch (NoVideoAvailableException e) {
                //String message = e.getMessage();
                logger.log(Level.INFO, "No video is available for the order " + order);
            }

            if (!order.isEmpty()) {
                this.setChanged();
                this.notifyObservers(order);
                return order;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
            return null;
        }
        return null;
    }

    public void createTestOrder() {
        try {
            TestOrder order = new TestOrder(this);
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            try {
                advertisementManager.processVideos();
            } catch (NoVideoAvailableException e) {
                //String message = e.getMessage();
                logger.log(Level.INFO, "No video is available for the order " + order);
            }

            if (!order.isEmpty()) {
                this.setChanged();
                this.notifyObservers(order);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
