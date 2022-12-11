package com.javarush.task.task32.task3204;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Генератор паролей
*/

public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        List<Character> list = makePasList();
        Collections.shuffle(list);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for(Character character: list) {
            byteArrayOutputStream.write(character);
        }
        return byteArrayOutputStream ;
    }

    public static List<Character> makePasList() {
        List<Character> list = new ArrayList<>();
        boolean isDigital = false;
        boolean isBigChar = false;
        boolean isSmallChar = false;
        for (int i = 0; i < 8; i++) {
            int switcher =(int) (Math.random() * 3);
            switch (switcher) {
                case 0:
                    list.add((char) ('a' + (int) (Math.random() * 26)));
                    isSmallChar = true;
                    break;
                case 1:
                    list.add((char) ('A' + (int) (Math.random() * 26)));
                    isBigChar = true;
                    break;
                case 2:
                    list.add((char) ('0' + (int) (Math.random() * 10)));
                    isDigital = true;
                    break;
            }
        }
        if (isDigital & isBigChar & isSmallChar) {
            return list;
        } else {
            return makePasList();
        }
    }

}
