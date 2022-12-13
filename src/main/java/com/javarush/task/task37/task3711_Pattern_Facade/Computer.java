package com.javarush.task.task37.task3711_Pattern_Facade;

public class Computer {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();
    private HardDrive hardDrive = new HardDrive();

    public void run() {
        cpu.calculate();
        memory.allocate();
        hardDrive.storeData();
    }
}
