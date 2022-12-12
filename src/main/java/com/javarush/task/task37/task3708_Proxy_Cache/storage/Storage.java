package com.javarush.task.task37.task3708_Proxy_Cache.storage;

public interface Storage {
    void add(Object storedObject);

    Object get(long id);
}
