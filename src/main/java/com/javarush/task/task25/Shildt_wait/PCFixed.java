package com.javarush.task.task25.Shildt_wait;

public class PCFixed {
    public static void main(String[] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
        System.out.println("Press push ctrl-c");

    }
}
