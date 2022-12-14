package com.javarush.task.task33.task3310_HashMap;

import com.javarush.task.task33.task3310_HashMap.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310_HashMap.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {


    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date startTimestamp = new Date();
        for (String s : strings)
            ids.add(shortener.getId(s));
        Date endTimestamp = new Date();
        return endTimestamp.getTime() - startTimestamp.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date startTimestamp = new Date();
        for (Long id : ids)
            strings.add(shortener.getString(id));
        Date endTimestamp = new Date();
        return endTimestamp.getTime() - startTimestamp.getTime();
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();
        Long idToStringTime1  = getTimeToGetIds(shortener1, origStrings, ids1);
        Long idToStringTime2  = getTimeToGetIds(shortener2, origStrings, ids2);
        Assert.assertTrue(idToStringTime1  > idToStringTime2);

        Set<String> strings1 = new HashSet<>();
        Set<String> strings2 = new HashSet<>();
        Long IdToStringTime1 = getTimeToGetStrings(shortener1, ids1, strings1);
        Long IdToStringTime2 = getTimeToGetStrings(shortener1, ids2, strings2);
        Assert.assertEquals(IdToStringTime1, IdToStringTime2, 30f);
    }
}
