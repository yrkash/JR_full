package com.javarush.task.task37.task3710_Pattern_Decorator.decorators;

import com.javarush.task.task37.task3710_Pattern_Decorator.shapes.Shape;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    private void setBorderColor(Shape shape) {
        System.out.println("Setting the border color for " +  shape.getClass().getSimpleName() + " to red.");
    }

    @Override
    public void draw() {
        setBorderColor(decoratedShape);
        super.draw();
    }
}

