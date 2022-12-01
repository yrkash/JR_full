package com.javarush.task.task37.task3702_FactoryMethod.male;

import com.javarush.task.task37.task3702_FactoryMethod.AbstractFactory;
import com.javarush.task.task37.task3702_FactoryMethod.Human;

public class MaleFactory implements AbstractFactory {
    public Human getPerson(int age) {
        if ( age <= KidBoy.MAX_AGE) {
            return new KidBoy();
        } else {
            if (age > KidBoy.MAX_AGE && age <= TeenBoy.MAX_AGE) {
                return new TeenBoy();
            } else {
                return new Man();
            }
        }
    }
}
