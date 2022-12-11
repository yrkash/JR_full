package com.javarush.task.task25.Shildt_wait;

public class Consumer implements Runnable {
    Q q;

    public Consumer(Q q) {
        this.q = q;
        new Thread(this, "Потребитель").start();
    }

    @Override
    public void run() {
        while (true) {
            q.get();
            if (q.n > 10) break;
        }
    }
}
