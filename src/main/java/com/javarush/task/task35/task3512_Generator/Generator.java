package com.javarush.task.task35.task3512_Generator;

public class Generator<T> {
    Class<T> clazz;

    public Generator(Class<T> clazz) {
        this.clazz = clazz;
    }

    T newInstance() throws InstantiationException, IllegalAccessException {


        return clazz.newInstance();
    }
}
