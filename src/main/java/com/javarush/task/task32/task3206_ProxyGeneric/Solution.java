package com.javarush.task.task32.task3206_ProxyGeneric;


import java.lang.reflect.Proxy;

/*
Дженерики для создания прокси-объекта
*/

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        test(solution.getProxy(Item.class));                        //true false false
        test(solution.getProxy(Item.class, Small.class));           //true false true
        test(solution.getProxy(Item.class, Big.class, Small.class));//true true true
        test(solution.getProxy(Big.class, Small.class));            //true true true т.к. Big наследуется от Item
        test(solution.getProxy(Big.class));                         //true true false т.к. Big наследуется от Item
    }


    private static void test(Object proxy) {
        boolean isItem = proxy instanceof Item;
        boolean isBig = proxy instanceof Big;
        boolean isSmall = proxy instanceof Small;

        System.out.format("%b %b %b\n", isItem, isBig, isSmall);
    }

    public <T extends Item> T getProxy(Class<T> tClass, Class... tInterfacesArray) {

        ClassLoader classLoader = tClass.getClassLoader();
        Class[] interfacesArray = new Class[tInterfacesArray.length + 1];
        interfacesArray[0] = tClass;
        System.arraycopy(tInterfacesArray, 0, interfacesArray, 1, tInterfacesArray.length);

        return (T) Proxy.newProxyInstance( this.getClass().getClassLoader(), interfacesArray, new ItemInvocationHandler() );

    }
}
