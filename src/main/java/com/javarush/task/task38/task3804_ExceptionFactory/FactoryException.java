package com.javarush.task.task38.task3804_ExceptionFactory;

public class FactoryException extends Throwable {

    public static Throwable getException(Enum myEnum) {
        if (myEnum == null) return new IllegalArgumentException();
            String message = myEnum.name().charAt(0) + myEnum.name().substring(1).toLowerCase().replace("_", " ");
            if (myEnum.getClass() == ApplicationExceptionMessage.class) {
                return new Exception(message);
            }
            if (myEnum.getClass() == DatabaseExceptionMessage.class) {
                return new RuntimeException(message);
            }
            if (myEnum.getClass() == UserExceptionMessage.class) {
                return new Error(message);
            }

        return new IllegalArgumentException();

    }

}