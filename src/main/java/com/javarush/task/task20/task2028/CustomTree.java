package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable{

    Entry<String> root;
    public int size = 0;

    public List<Entry<String>> list = new LinkedList<>();

    public CustomTree() {
        this.root = new Entry<>("root");
        root.parent = root;
        list.add(root);
    }
    @Override
    public int size() {
        return this.size;
    }

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
    @Override
    public boolean add(String s) {
        size++;
        boolean recoverToAddChildren = true;
        int currentSize = this.size();
        Entry<String> newNode = new Entry<>(s);



        for (int i = 0; i < currentSize; i++) {
            if (list.get(i).isAvailableToAddChildren()) {
                recoverToAddChildren = false;
                newNode.parent = list.get(i);
                if (list.get(i).availableToAddLeftChildren) {
                    list.get(i).availableToAddLeftChildren = false;
                    list.get(i).leftChild = newNode;
                } else {
                    list.get(i).availableToAddRightChildren = false;
                    list.get(i).rightChild = newNode;
                }

                list.add(newNode);
                break;
            }
        }

        if (recoverToAddChildren) {
//            System.out.println("Need to recover");
            for(Entry<String> entry: list) {
                if (entry.rightChild == null) entry.availableToAddRightChildren = true;
                if (entry.leftChild == null) entry.availableToAddLeftChildren = true;
            }
            for (int i = 0; i < currentSize; i++) {
                if (list.get(i).isAvailableToAddChildren()) {

                    newNode.parent = list.get(i);
                    if (list.get(i).availableToAddLeftChildren) {
                        list.get(i).availableToAddLeftChildren = false;
                        list.get(i).leftChild = newNode;
                    } else {
                        list.get(i).availableToAddRightChildren = false;
                        list.get(i).rightChild = newNode;
                    }

                    list.add(newNode);
                    break;
                }
            }
        }
//        System.out.println("Parent of " + s + " is " + this.getParent(s));
        return true;
    }

    @Override
    public boolean remove (Object o) {
        boolean b = (o instanceof String);
        if (!b) throw new UnsupportedOperationException();

        if (getEntryByName((String) o) == null) return false;
        // Рекурсивно удаляет сначала всех потомков, потом сам объект Entry<String>
        removeChild(getEntryByName((String) o));
        return false;
    }

    void removeChild (Entry<String> current) {

        if (current.leftChild != null) {
            removeChild(current.leftChild);
        }
        if (current.rightChild != null) {
            removeChild(current.rightChild);
        }
        current.leftChild = null;
        current.rightChild = null;
        Entry<String> currentParent = current.parent;
        if (current == currentParent.leftChild) current.parent.leftChild = null;
        if (current == currentParent.rightChild) current.parent.rightChild = null;
        list.remove(current);
        size--;
    }


    public Entry<String> getEntryByName(String name) {
        Entry<String> result = null;
        for (Entry<String> entry: list) {
            if (entry.elementName.equals(name)) {
                result = entry;
                break;
            }
        }
        return result;

    }


    public String getParent(String s) {
        String parentName = null;
        for (Entry<String> entry: list) {
            if (entry.elementName.equals(s)) {
                parentName = entry.parent.elementName;
                break;
            }
        }
        return parentName;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }


}
