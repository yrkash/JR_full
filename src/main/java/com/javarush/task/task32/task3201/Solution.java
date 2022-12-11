package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/*
Запись в существующий файл
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        int position = number < raf.length() ? number : (int) raf.length();
        raf.seek(position);
        //печатаем в файл строку surprise!
        raf.write(text.getBytes());

        //закрываем файл
        raf.close();

    }
}
