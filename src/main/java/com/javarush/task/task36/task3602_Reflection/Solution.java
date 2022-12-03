package com.javarush.task.task36.task3602_Reflection;

import java.lang.reflect.*;
import java.util.*;

/*
Найти класс по описанию Ӏ Java Collections: 6 уровень, 6 лекция
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class[] classes = Collections.class.getDeclaredClasses();
        for (int i = 0; i < classes.length; i++) {
            Class clazz = classes[i];
            if (List.class.isAssignableFrom(clazz) && clazz.getModifiers() == 10) {
                try {
                    Constructor constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
//                    Method method = clazz.getDeclaredMethod("get", int.class);
//                    method.setAccessible(true);
//                    method.invoke(constructor.newInstance(),0);
                    List list = (List) constructor.newInstance();
                    list.get(0);
                } catch (IndexOutOfBoundsException e){
                    return clazz;
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                }
            }
        }
        return null;
    }
}
