package com.javarush.task.task38.task3804_ExceptionFactory;

/*
Фабрика исключений
*/

public class Solution {
    public static Class getFactoryClass() {
        return FactoryException.class;
    }

    public static void main(String[] args) {
        FactoryException.getException(ApplicationExceptionMessage.UNHANDLED_EXCEPTION);
        FactoryException exFab=new FactoryException();
        System.out.println(exFab.getException(ApplicationExceptionMessage.UNHANDLED_EXCEPTION));
        System.out.println(exFab.getException(ApplicationExceptionMessage.SOCKET_IS_CLOSED));
        System.out.println(exFab.getException(DatabaseExceptionMessage.NOT_ENOUGH_CONNECTIONS));
        System.out.println(exFab.getException(DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT));
        System.out.println(exFab.getException(UserExceptionMessage.USER_DOES_NOT_EXIST));
        System.out.println(exFab.getException(UserExceptionMessage.USER_DOES_NOT_HAVE_PERMISSIONS));
        System.out.println(exFab.getException(null));
    }
}