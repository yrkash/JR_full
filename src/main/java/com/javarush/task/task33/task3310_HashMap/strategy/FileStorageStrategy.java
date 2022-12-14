package com.javarush.task.task33.task3310_HashMap.strategy;

public class FileStorageStrategy implements StorageStrategy{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 1000;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;

    public FileStorageStrategy() {
        init();
    }

    private void init() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }


    @Override
    public boolean containsKey(Long key) {
        Entry entry = getEntry(key);
        return (entry == null)? false : true;
    }

    @Override
    public boolean containsValue(String value) {
        for (int i = 0; i < table.length; i++) {
            Entry entry = table[i].getEntry();
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
        for (Entry entry = table[i].getEntry(); entry != null; entry = entry.next) {
            if (key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }

    @Override
    public Long getKey(String value) {
        for (int i = 0; i < table.length; i++) {
            Entry entry = table[i].getEntry();
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
        for (Entry entry = table[indexFor(hash, table.length)].getEntry(); entry != null; entry = entry.next) {
            if (key.equals(entry.key)) {
                return entry;
            }
        }
        return null;
    }
    void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];

        for (int i = 0; i < newTable.length; i++)
            newTable[i] = new FileBucket();

        transfer(newTable);

        for (int i = 0; i < table.length; i++)
            table[i].remove();

        table = newTable;
    }

    void transfer(FileBucket[] newTable) {
        int newCapacity = newTable.length;
        maxBucketSize = 0;

        for (FileBucket fileBucket : table) {
            Entry entry = fileBucket.getEntry();
            while (entry != null) {
                Entry next = entry.next;
                int indexInNewTable = indexFor(entry.getKey().hashCode(), newCapacity);
                entry.next = newTable[indexInNewTable].getEntry();
                newTable[indexInNewTable].putEntry(entry);
                entry = next;
            }

            long currentBucketSize = fileBucket.getFileSize();
            if (currentBucketSize > maxBucketSize)
                maxBucketSize = currentBucketSize;
        }
    }


    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        if (maxBucketSize > bucketSizeLimit)
            resize(2 * table.length);
        createEntry(hash,key,value,bucketIndex);
    }
    void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;

        long currentBucketSize = table[bucketIndex].getFileSize();
        if (currentBucketSize > maxBucketSize)
            maxBucketSize = currentBucketSize;
    }
}
