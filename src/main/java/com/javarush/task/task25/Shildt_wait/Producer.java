package com.javarush.task.task25.Shildt_wait;

public class Producer implements Runnable{
    Q q;

    public Producer(Q q) {
        this.q = q;
        new Thread(this,"Поставщик").start();
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            q.put(i++);
            if (q.n > 10) break;
        }
    }
}
