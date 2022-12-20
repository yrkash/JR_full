package com.javarush.task.task38.task3810_Annotation;

public @interface Author {
    String value();
    Position position() default Position.OTHER;
}
