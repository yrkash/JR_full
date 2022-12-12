package com.javarush.task.task37.task3708_Proxy_Cache.retrievers;

import com.javarush.task.task37.task3708_Proxy_Cache.cache.LRUCache;
import com.javarush.task.task37.task3708_Proxy_Cache.storage.Storage;

public class CachingProxyRetriever implements Retriever {

    private LRUCache<Long, Object> cache = new LRUCache<>(16);
    private OriginalRetriever retriever;

    public CachingProxyRetriever(Storage storage) {
        retriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        Object result = cache.find(id);
        if (result == null) {
            Object resultFromStorage = retriever.retrieve(id);
            cache.set(id, resultFromStorage);
            return resultFromStorage;
        }
        return result;
    }
}
