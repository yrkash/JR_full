package com.javarush.task.task20.task2015;

import java.io.*;

/*
Переопределение сериализации
*/

public class Solution implements Serializable, Runnable{
    private transient Thread runner;
    private int speed;

    public Solution(int speed) {
        this.speed = speed;
        runner = new Thread(this);
        runner.start();

    }

    public void run() {
        // do something here, doesn't matter what
        System.out.println("Thread is running now");
    }

    /**
     * Переопределяем сериализацию.
     * Для этого необходимо объявить методы:
     * private void writeObject(ObjectOutputStream out) throws IOException
     * private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
     * Теперь сериализация/десериализация пойдет по нашему сценарию :)
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this);
        //out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.readObject();
        start();
    }

    private void start(){
        runner = new Thread(this);
        runner.start();
    }

    public static void main(String[] args) {

        try {
            Solution solution = new Solution(10);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("c:/target/test.txt"));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("c:/target/test.txt"));
            solution.writeObject(oos);
            Solution loadedSolution = new Solution(15);
            loadedSolution.readObject(ois);
            System.out.println(solution.speed == loadedSolution.speed);
            oos.close();
            ois.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
