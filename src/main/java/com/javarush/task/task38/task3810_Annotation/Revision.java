package com.javarush.task.task38.task3810_Annotation;

public @interface Revision {
    int revision();
    Date date();
    Author[] authors() default {};
    String comment() default "";
}
