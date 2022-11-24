    package com.javarush.task.task34.task3408_Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {


    private Map<K, V> cache = new WeakHashMap<K, V>();   //TODO add your code here

    public V getByKey(K key, Class<V> clazz) throws Exception {
        //TODO add your code here
        V value = cache.get(key);
        if (value == null) {
            V instance = clazz.getConstructor(key.getClass()).newInstance(key);
            cache.put(key, instance);
            value = cache.get(key);
        }
        return value;
    }

    public boolean put(V obj) {
        //TODO add your code here

        try {
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            K key = (K) method.invoke(obj);
            cache.put(key, obj);
            return true;
        }  catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
            return false;
        }
    }

    public int size() {
        return cache.size();
    }
}
