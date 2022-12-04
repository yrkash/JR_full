package com.javarush.task.task36.task3606_ClassLoader_Reflection;

import java.io.*;
import java.nio.file.Files;

public class MyClassLoader extends ClassLoader {

    private String path;

    public MyClassLoader(String path) {
        this.path = path;
    }

    public Class<?> findClass(String name) {
        byte[] b = loadClassData(name);
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


}
