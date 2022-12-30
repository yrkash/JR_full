package com.javarush.task.task39.task3906;

public class ElectricPowerSwitch {
    private Switchable electricSystem;

    public ElectricPowerSwitch(Switchable electricSystem) {
        this.electricSystem = electricSystem;
    }

    public void press() {
        System.out.println("Power switch flipped.");
        if (electricSystem.isOn()) {
            electricSystem.turnOff();
        } else {
            electricSystem.turnOn();
        }
    }
}
