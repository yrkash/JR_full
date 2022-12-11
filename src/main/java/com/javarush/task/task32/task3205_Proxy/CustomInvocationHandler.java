package com.javarush.task.task32.task3205_Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {

    private SomeInterfaceWithMethods implOriginal;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(method.getName() + " in");
        Object result = method.invoke(implOriginal,args);
        System.out.println(method.getName() + " out");
        return result;
    }

    public CustomInvocationHandler(SomeInterfaceWithMethods implOriginal) {
        this.implOriginal = implOriginal;
    }
}
