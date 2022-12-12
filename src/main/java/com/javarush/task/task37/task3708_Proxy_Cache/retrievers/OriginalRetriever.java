package com.javarush.task.task37.task3708_Proxy_Cache.retrievers;

import com.javarush.task.task37.task3708_Proxy_Cache.storage.Storage;

public class OriginalRetriever implements Retriever {
    Storage storage;

    public OriginalRetriever(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Object retrieve(long id) {
        return storage.get(id);
    }
}
