package com.javarush.task.task36.task3604_RedBlackTree;

public class AppearanceException extends RuntimeException {

    public AppearanceException(String appearance) {
        super("Inappropriate appearance value: " + appearance);
    }
}