package com.javarush.task.task25.pingpong;

public class Main {

    public static void main(String[] args) {
        PingPongGame game = new PingPongGame();
        try {
            game.startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
