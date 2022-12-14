package com.javarush.task.task33.task3310_HashMap;

import com.javarush.task.task33.task3310_HashMap.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String str1 = new String("Test string 1");
        String str2 = new String("Test string 2");
        String str3 = new String("Test string 1");

        Long id1 = shortener.getId(str1);
        Long id2 = shortener.getId(str2);
        Long id3 = shortener.getId(str3);

        Assert.assertNotEquals(id1, id2);
        Assert.assertNotEquals(id3, id2);
        Assert.assertEquals(id1, id3);

        String actualStr1 = shortener.getString(id1);
        String actualStr2 = shortener.getString(id2);
        String actualStr3 = shortener.getString(id3);

        Assert.assertEquals(str1, actualStr1);
        Assert.assertEquals(str2, actualStr2);
        Assert.assertEquals(str3, actualStr3);
    }
    @Test
    public void testHashMapStorageStrategy() {
        HashMapStorageStrategy storageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy() {
        FileStorageStrategy storageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy() {
        HashBiMapStorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy() {
        DualHashBidiMapStorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy() {
        OurHashBiMapStorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
}