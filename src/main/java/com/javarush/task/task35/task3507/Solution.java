package com.javarush.task.task35.task3507;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
ClassLoader - что это такое?
*/

public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
        System.out.println(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");

    }

    static class CustomClassLoader extends ClassLoader {
//        String path;

        @Override
        public Class<?> loadClass(String path) throws ClassNotFoundException {
            byte[] buffer = new byte[(int) new File(path).length()];
            try(InputStream inputStream = new FileInputStream(new File(path)) ) {
                buffer = inputStream.readAllBytes();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(null, buffer, 0, buffer.length);
//            return super.loadClass(name);
        }

        public Class findClass(String name) {
            byte[] b = loadClassData(name);
            return defineClass(null, b, 0, b.length);
        }

        private byte[] loadClassData(String name) {
            // load the class data from the connection
            byte[] buffer = new byte[(int) new File(name).length()];
            try(InputStream inputStream = new FileInputStream(new File(name)) ) {
                buffer = inputStream.readAllBytes();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer;
        }
    }


    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> result = new HashSet<>();
        boolean hasInterface = false;
        boolean hasConstructor = false;

        File dir = new File(pathToAnimals);
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);

        CustomClassLoader classLoader = new CustomClassLoader();

        for(File file: lst) {
            String className = file.getName().replace(".class","");
            Class clazz = null;
            try {
                clazz = classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class[] interfaces = clazz.getInterfaces();
            for (Class i : interfaces) {
                if (Animal.class == i) {
                    hasInterface = true;
                    break;
                }
            }
            if (!hasInterface) continue;

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
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}
