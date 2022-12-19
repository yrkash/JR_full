package com.javarush.task.task38.task3802;

/*
Проверяемые исключения (checked exception)
*/

import java.io.FileInputStream;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        //напишите тут ваш код
        FileInputStream fileInputStream = new FileInputStream("c:\temp\second_.txt");        System.out.println(1 / 0);
    }

    public static void main(String[] args) {
        VeryComplexClass instance = new VeryComplexClass();
        try {
            instance.veryComplexMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
