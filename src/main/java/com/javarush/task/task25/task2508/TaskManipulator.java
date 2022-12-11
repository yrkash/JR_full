package com.javarush.task.task25.task2508;

import java.util.spi.AbstractResourceBundleProvider;

public class TaskManipulator implements CustomThreadManipulator, Runnable {
    private Thread thread;
    @Override
    public void start(String threadName) {
        thread = new Thread(this, threadName);
        thread.start();
    }

    @Override
    public void stop() {
        this.thread.interrupt();
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            System.out.println(thread.getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }
}
