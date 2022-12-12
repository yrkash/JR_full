package com.javarush.task.task37.task3710_Pattern_Decorator.shapes;

public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a shape: CIRCLE!");
    }
}
