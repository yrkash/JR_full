package com.javarush.task.task35.task3507_ClassLoader;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/*
ClassLoader - что это такое?
*/

public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        if (!pathToAnimals.endsWith("\\") && !pathToAnimals.endsWith("/"))
            pathToAnimals = pathToAnimals + "/";
        Set<Animal> result = new HashSet<>();
        boolean hasInterface = false;
        boolean hasConstructor = false;

        File dir = new File(pathToAnimals);
        String[] pathes = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir1, String name) {
                return name.toLowerCase().endsWith(".class");
            }
        });
        String finalPathToAnimals = pathToAnimals;
//      Анонимный класс extended ClassLoader. Сделан, чтобы загружать файлы по простому имени (Например, Cat)
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> findClass(String name) {
                byte[] b = loadClassData(finalPathToAnimals + name + ".class");
                return defineClass(null, b, 0, b.length);
            }

            private byte[] loadClassData(String name) {
                // load the class data from the connection
                byte[] buffer = new byte[(int) new File(name).length()];
                try(InputStream inputStream = new FileInputStream(name) ) {
                    buffer = Files.readAllBytes(new File(name).toPath());
//                    buffer = inputStream.readAllBytes();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return buffer;
            }
        };


        for(String path: pathes) {
            String className = path.replace(".class","");
            Class clazz = null;
            try {
                clazz = classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
//          ПРоверка, что наш класс clazz имплементирует интерфейс Animal
            Class[] interfaces = clazz.getInterfaces();
            for (Class i : interfaces) {
                if (Animal.class == i) {
                    hasInterface = true;
                    break;
                }
            }
            if (!hasInterface) continue;

//          ПРоверка, что наш класс clazz имеет конструктор по умолчанию
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor c : constructors) {
                if (c.getParameterTypes().length == 0) {
                    hasConstructor = true;
                    break;
                }
            }
            if (!hasConstructor) continue;
            try {
                result.add((Animal) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException ignore) {

            }
        }
        return result;
    }
}
