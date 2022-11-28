package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <K,V extends Convertable<K>> Map<K, V> convert(List<V> list) {

        Map<K, V> map = new HashMap<K, V>();
        for (V element: list) {
            K key =  element.getKey();
            map.put(key, element);
        }

        return map;
    }
}
