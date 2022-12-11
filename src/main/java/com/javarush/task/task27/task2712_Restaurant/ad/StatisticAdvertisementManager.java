package com.javarush.task.task27.task2712_Restaurant.ad;

import java.util.*;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager statisticAdvertisementManager;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        if (statisticAdvertisementManager == null) {
            statisticAdvertisementManager = new StatisticAdvertisementManager();
        }
        return statisticAdvertisementManager;
    }

    public Map<String, Integer> getActiveVideoMap() {
        Map<String, Integer> activeVideoMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        List<Advertisement> advertisementList = advertisementStorage.list();
        for(Advertisement advertisement: advertisementList) {
            if (advertisement.getHits() > 0) {
                activeVideoMap.put(advertisement.getName(), advertisement.getHits());
            }
        }
        return activeVideoMap;
    }

    public Set<String> getArchivedVideoSet() {
        TreeSet<String> archivedVideoSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        List<Advertisement> advertisementList = advertisementStorage.list();
        for (Advertisement advertisement: advertisementList) {
            if (advertisement.getHits() == 0) {
                archivedVideoSet.add(advertisement.getName());
            }
        }
        return archivedVideoSet;
    }

    public List<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> result = new ArrayList<>();
        for (Advertisement advertisement : advertisementStorage.list()) {
            if (!isActive ^ advertisement.isActive()) {
                result.add(advertisement);
            }
        }
        return result;
    }

}
