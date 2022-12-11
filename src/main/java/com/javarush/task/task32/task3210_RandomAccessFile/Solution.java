package com.javarush.task.task32.task3210_RandomAccessFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/*
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        raf.seek(number);
        byte [] bytes = new byte[args[2].length()];
        //читаем raf в поток байт
        raf.read(bytes, 0, text.length());

        int position = (int) raf.length();
        raf.seek(position);
        if (text.equals(new String(bytes, StandardCharsets.UTF_8))) {
            raf.write(new String("true").getBytes());
        } else {
            raf.write(new String("false").getBytes());
        }
        //закрываем файл
        raf.close();
    }
}
