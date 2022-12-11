package com.javarush.task.task27.task2712_Restaurant;

import com.javarush.task.task27.task2712_Restaurant.ad.Advertisement;
import com.javarush.task.task27.task2712_Restaurant.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712_Restaurant.statistic.StatisticManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<Date, Double> map = statisticManager.calcTotalAdvertisementAmountPerDay();
        Double totalAmount = 0d;
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date,Double> entry: map.entrySet()) {
            totalAmount += entry.getValue();
            String message = String.format("%s - %.2f", df.format(entry.getKey()), entry.getValue());
            ConsoleHelper.writeMessage(message);
        }
        String total = String.format("Total - %.2f", totalAmount);
        ConsoleHelper.writeMessage(total);
    }

    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map <Date, Map<String, Integer>> map = statisticManager.calcCookingTimePerDay();
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, Map<String, Integer>> entry: map.entrySet()) {
            ConsoleHelper.writeMessage(df.format(entry.getKey()));
            for (Map.Entry<String, Integer> subEntry: entry.getValue().entrySet()) {
                String message = String.format("%s - %d min", subEntry.getKey(), subEntry.getValue());
                ConsoleHelper.writeMessage(message);
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> videoSet = StatisticAdvertisementManager.getInstance().getVideoSet(true);
        Collections.sort(videoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });

        for (Advertisement advertisement : videoSet) {
            System.out.println(advertisement.getName() + " - " + advertisement.getHits());
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> videoSet = StatisticAdvertisementManager.getInstance().getVideoSet(false);
        Collections.sort(videoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });

        for (Advertisement advertisement : videoSet) {
            System.out.println(advertisement.getName());
        }
    }
}
