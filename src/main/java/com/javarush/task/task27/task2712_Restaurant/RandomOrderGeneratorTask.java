package com.javarush.task.task27.task2712_Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RandomOrderGeneratorTask implements Runnable{

    private List<Tablet> tabletList = new ArrayList<>();
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tabletList, int interval) {
        this.tabletList = tabletList;
        this.interval = interval;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(interval);
                int tabletCount = tabletList.size();
                tabletList.get((int) (Math.random() * (tabletCount - 1))).createTestOrder();
            } catch (InterruptedException e) {
                return;
            }

        }

    }
}
