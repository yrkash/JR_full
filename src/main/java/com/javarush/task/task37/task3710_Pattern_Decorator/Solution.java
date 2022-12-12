package com.javarush.task.task37.task3710_Pattern_Decorator;

import com.javarush.task.task37.task3710_Pattern_Decorator.decorators.RedShapeDecorator;
import com.javarush.task.task37.task3710_Pattern_Decorator.shapes.Circle;
import com.javarush.task.task37.task3710_Pattern_Decorator.shapes.Rectangle;
import com.javarush.task.task37.task3710_Pattern_Decorator.shapes.Shape;

/*
Decorator
*/

public class Solution {
    public static void main(String[] args) {
        Shape circle = new Circle();
        Shape redCircle = new RedShapeDecorator(new Circle());
        Shape redRectangle = new RedShapeDecorator(new Rectangle());

        System.out.println("Simple circle");
        circle.draw();

        System.out.println("\nApplied RedShapeDecorator to the circle");
        redCircle.draw();

        System.out.println("\nApplied RedShapeDecorator to the rectangle");
        redRectangle.draw();
    }
}
