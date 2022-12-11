package com.javarush.task.task25.Shildt_deadlock;

public class B {
    synchronized void bar(A a) {
        String name = Thread.currentThread().getName();

        System.out.println(name + " вошел в метод  B.bar()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Класс B прерван");
        }
        System.out.println(name + " пытается вызвать метод A.last()");
        a.last();
    }

    synchronized void last() {
        System.out.println("В методе A.last()");
    }
}
