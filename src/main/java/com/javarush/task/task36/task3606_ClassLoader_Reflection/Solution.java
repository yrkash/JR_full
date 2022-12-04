package com.javarush.task.task36.task3606_ClassLoader_Reflection;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/*
Осваиваем ClassLoader и Reflection
*/

public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + "com/javarush/task/task36/task3606_ClassLoader_Reflection/data/second");
        solution.scanFileSystem();
//        System.out.println(solution.hiddenClasses);
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File[] files = new File(packageName).listFiles();
        for (File file: files) {
            if (file.getAbsolutePath().endsWith(".class")) {
//                System.out.println(file.getAbsolutePath());
                MyClassLoader classLoader = new MyClassLoader(file.getAbsolutePath());
                Class clazz = classLoader.loadClass(file.getAbsolutePath());
                hiddenClasses.add(clazz);
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        String keyToLowerCase = key.toLowerCase();
        for (Class clazz: hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(keyToLowerCase)) {
                try {
                    Constructor constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    HiddenClass instance = (HiddenClass) constructor.newInstance();
                    return instance;
                } catch (NoSuchMethodException e) {
//                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
//                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
                }
            }
        }
        return null;

    }




}

