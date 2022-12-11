package com.javarush.task.task26.task2603;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
Убежденному убеждать других не трудно
*/

public class Solution {

    public static void main(String[] args) {

        TestClass first=new TestClass("aaa","bbb","ccc");
        TestClass second=new TestClass("aaa","ccc","hrt");
        TestClass third=new TestClass("aaa","ccc","aaa");
        ArrayList<TestClass> list=new ArrayList<>();
        list.add(first);list.add(second);list.add(third);

        Collections.sort(list,new CustomizedComparator<TestClass>(new Comparator<TestClass>() {
            @Override
            public int compare(TestClass o1, TestClass o2) {
                return o1.x.compareTo(o2.x);
            }
        }, new Comparator<TestClass>() {
            @Override
            public int compare(TestClass o1, TestClass o2) {
                return o1.y.compareTo(o2.y);
            }
        }, new Comparator<TestClass>() {
            @Override
            public int compare(TestClass o1, TestClass o2) {
                return o1.z.compareTo(o2.z);
            }
        }));

        for (TestClass test:list) {
            test.consoleout();
        }
    }

        public static class CustomizedComparator <T> implements Comparator<T> {

            private Comparator<T>[] comparators;

            public CustomizedComparator(Comparator<T>... comparators) {
                this.comparators = comparators;
            }

            @Override
            public int compare(T o1, T o2) {
                int length = comparators.length;
                for (int i = 0; i < length; i++) {
                    int diff = comparators[i].compare(o1, o2);
                    if (diff != 0) return diff;
                    //break;
                }
                return 0;
            }

        }
}
