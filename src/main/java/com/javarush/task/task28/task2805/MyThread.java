package com.javarush.task.task28.task2805;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {

    private static final AtomicInteger threadPriority = new AtomicInteger(1);

    private synchronized void correctPriority() {
        if (threadPriority.intValue() > MAX_PRIORITY) {
            threadPriority.set(MIN_PRIORITY);
        }
        if ((this.getThreadGroup() != null) && (threadPriority.get() > this.getThreadGroup().getMaxPriority())) {
            this.setPriority(this.getThreadGroup().getMaxPriority());
            threadPriority.incrementAndGet();
        } else {
            this.setPriority(threadPriority.getAndIncrement());
        }
    }

    public MyThread() {
        super();
        correctPriority();

    }

    //1392,47 + 515,46

    public MyThread(Runnable target) {
        super(target);
        correctPriority();

    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        correctPriority();
    }

    public MyThread(String name) {

        super(name);
        correctPriority();
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        correctPriority();
    }

    public MyThread(Runnable target, String name) {

        super(target, name);
        correctPriority();
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {

        super(group, target, name);
        correctPriority();
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        correctPriority();
    }


}
