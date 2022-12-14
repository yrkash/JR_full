package com.javarush.task.task33.task3310_HashMap.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy{

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    @Override
    public boolean containsKey(Long key) {
        Entry entry = getEntry(key);
        return (entry == null)? false : true;
    }

    @Override
    public boolean containsValue(String value) {
        for (Entry entry: table) {
            for (Entry e = entry; e != null; e = e.next) {
                if (value.equals(e.value)) return true;
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {

        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                e.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }

    @Override
    public Long getKey(String value) {
        for (Entry entry: table) {
            for (Entry e = entry; e != null; e = e.next) {
                if (value.equals(e.value)) return e.getKey();
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        String result = entry.getValue();
        if (result != null) {
            return result;
        }
        return null;
    }


    public int hash(Long k) {
        return k.hashCode();
    }

    public int indexFor(int hash, int length) {
        return hash & (length-1);
    }
    public Entry getEntry(Long key) {
        int hash = hash(key);
        for (Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return e;
            }
        }
        return null;
    }
    public void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == DEFAULT_INITIAL_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }
    void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }
    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        if (size++ >= threshold)
            resize(2 * table.length);
    }
    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }
}
