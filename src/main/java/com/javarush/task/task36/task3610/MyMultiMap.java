package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int size = 0;
        for (Map.Entry<K, List<V>> entry: map.entrySet()) {
            size += entry.getValue().size();
        }
        //напишите тут ваш код
        return size;
    }

    @Override
    public V put(K key, V value) {
        V resultValue = null;
        List<V> currentList = map.get(key);
        if (currentList != null) {
            map.remove(key);
            if (currentList.size() >= repeatCount) currentList.remove(0);
            if (currentList.size() > 0) resultValue = currentList.get(currentList.size() - 1);
            currentList.add(value);
            map.put(key,currentList);
        } else {
            ArrayList<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        }
        return resultValue;
    }

    @Override
    public V remove(Object key) {
        V resultValue = null;
        List<V> currentList = map.get(key);
        if (currentList != null) {
            if (currentList.size() > 0) {
                List<V> buffer = new ArrayList<>(currentList);
                resultValue = buffer.remove(0);
                map.remove(key);
                if (buffer.size() > 0) map.put((K) key,buffer);
            } else {
                map.remove(key);
            }
        }
        return resultValue;
        //напишите тут ваш код
    }

    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        ArrayList<V> list = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry: map.entrySet()) {
            list.addAll(entry.getValue());
        }
        return list;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.keySet().contains(key);
        //напишите тут ваш код
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
        //напишите тут ваш код
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}