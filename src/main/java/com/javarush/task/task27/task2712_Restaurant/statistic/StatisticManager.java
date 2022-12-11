package com.javarush.task.task27.task2712_Restaurant.statistic;

import com.javarush.task.task27.task2712_Restaurant.kitchen.Cook;
import com.javarush.task.task27.task2712_Restaurant.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712_Restaurant.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712_Restaurant.statistic.event.EventType;
import com.javarush.task.task27.task2712_Restaurant.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {
    private static StatisticManager instance;

    private Set<Cook> cooks = new HashSet<>();
    private StatisticStorage statisticStorage = new StatisticStorage();
    private StatisticManager() {
    }

    public Map<Date, Double> calcTotalAdvertisementAmountPerDay() {
        TreeMap<Date, Double> treeMap = new TreeMap<>(Collections.reverseOrder());
        Calendar calendar = Calendar.getInstance();

        //Map<EventType, List<EventDataRow>> storage = statisticStorage.getStorage();
        List<EventDataRow> list = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        for (EventDataRow eventDataRow: list) {
            VideoSelectedEventDataRow videoRow = (VideoSelectedEventDataRow) eventDataRow;
            calendar.setTime(videoRow.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            Date date = gregorianCalendar.getTime();
            if (treeMap.containsKey(date)) {
                Double curAmount = treeMap.get(date) + (double) (videoRow.getAmount()) / 100;
                treeMap.put(date, curAmount);
            } else {
                treeMap.put(date, (double) (videoRow.getAmount()) / 100);
            }
        }
        return treeMap;
    }

    public Map<Date, Map<String, Integer>> calcCookingTimePerDay() {
        TreeMap<Date, Map<String, Integer>> treeMap = new TreeMap<>(Collections.reverseOrder());
        Calendar calendar = Calendar.getInstance();

        //Map<EventType, List<EventDataRow>> storage = statisticStorage.getStorage();
        List<EventDataRow> list = statisticStorage.getStorage().get(EventType.COOKED_ORDER);
        for (EventDataRow eventDataRow: list) {
            CookedOrderEventDataRow cookRow = (CookedOrderEventDataRow) eventDataRow;
            calendar.setTime(cookRow.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            Date date = gregorianCalendar.getTime();
            if (treeMap.containsKey(date)) {
                if (treeMap.get(date).containsKey(cookRow.getCookName())) {
                    Integer currentCookingTime = treeMap.get(date).get(cookRow.getCookName());
                    Integer newCookingTime = cookRow.getTime() / 60;
                    Map<String, Integer> buffMap = treeMap.get(date);
                    buffMap.put(cookRow.getCookName(), currentCookingTime + newCookingTime);
                    treeMap.put(date, buffMap);
                } else {
                    Map<String, Integer> buffMap = treeMap.get(date);
                    buffMap.put(cookRow.getCookName(), cookRow.getTime() / 60);
                    treeMap.put(date, buffMap);
                }
            } else {
                Map<String, Integer> buffMap = new TreeMap<>();
                buffMap.put(cookRow.getCookName(), cookRow.getTime() / 60);
                treeMap.put(date, buffMap);
            }
        }
        return treeMap;
    }



    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType eventType: EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private void put (EventDataRow data) {

            if (storage.containsKey(data.getType())) { storage.get(data.getType()).add(data);}
        }

    }


    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }
        return instance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        this.cooks.add(cook);
    }

}
