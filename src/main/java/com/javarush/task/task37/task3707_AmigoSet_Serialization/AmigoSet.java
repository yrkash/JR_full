package com.javarush.task.task37.task3707_AmigoSet_Serialization;

import java.io.*;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E>   {

    private final static Object PRESENT = new Object();

    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (Math.ceil(collection.size()/.75f) > 16) ? (int) Math.ceil(collection.size() / .75f) : 16;
        this.map = new HashMap<>(capacity);
        for (E element: collection) {
            this.add(element);
        }
    }



    @Override
    public Object clone()  {
        try {
            AmigoSet<E> cloneSet = new AmigoSet<>();
            cloneSet.map = (HashMap<E, Object>) map.clone();
            return cloneSet;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(map.size());
        int capacity = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        out.writeInt(capacity);
        float loadFactor = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        out.writeFloat(loadFactor);
        for (E element: map.keySet()) {
            out.writeObject(element);
        }
        out.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = (int) in.readInt();
        int capacity = (int) in.readInt();
        float loadFactor = (float) in.readFloat();
           this.map = new HashMap<>(capacity, loadFactor);
        for (int i = 0; i < size; i++) {
            E e = (E) in.readObject();
            map.put(e, PRESENT);
        }
        in.close();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean add(E e) {
        if (map.containsKey(e)) {
            return false;
        } else {
            map.put(e, PRESENT);
            return true;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }
}
