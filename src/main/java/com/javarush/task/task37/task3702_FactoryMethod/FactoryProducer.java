package com.javarush.task.task37.task3702_FactoryMethod;

import com.javarush.task.task37.task3702_FactoryMethod.female.FemaleFactory;
import com.javarush.task.task37.task3702_FactoryMethod.male.MaleFactory;

public class FactoryProducer {

    public static enum HumanFactoryType {
        MALE, FEMALE
    }
    public static AbstractFactory getFactory(HumanFactoryType type) {
        if (type.equals(HumanFactoryType.MALE)) {
            return new MaleFactory();
        }
        else {
            return new FemaleFactory();
        }

    }
}
