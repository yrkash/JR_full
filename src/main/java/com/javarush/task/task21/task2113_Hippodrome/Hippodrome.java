package com.javarush.task.task21.task2113_Hippodrome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hippodrome {

    private List<Horse> horses;

    static Hippodrome game;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public static void main(String[] args) {
        ArrayList<Horse> list  = new ArrayList<>();
        Horse horse1 = new Horse ("black",3.0,0.0);
        Horse horse2 = new Horse ("white",3.0,0.0);
        Horse horse3 = new Horse ("brown",3.0,0.0);
        Collections.addAll(list,horse1, horse2, horse3);
        Hippodrome hippodrome = new Hippodrome(list);
        Hippodrome.game = hippodrome;
    }
}
