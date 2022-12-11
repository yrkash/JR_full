package com.javarush.task.task20.task2014_Serializable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/*
Serializable Solution
*/

public class Solution implements Serializable {
    public static void main(String[] args) {

        try {
            //yourFile = File.createTempFile("your_file_name", null);
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("c:/target/test.txt"));
            //ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("c:/target/test.txt"));
            Solution savedObject = new Solution(4);
            oos.writeObject(savedObject);
            Solution newObject = new Solution(5);
            Solution loadedObject = (Solution) ois.readObject();
            System.out.println(savedObject);
            System.out.println(loadedObject);
            System.out.println(savedObject.string.equals(loadedObject.string));
            oos.close();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //System.out.println(new Solution(4));
    }

    private transient final String pattern = "dd MMMM yyyy, EEEE";
    private transient Date currentDate;
    private transient int temperature;
    String string;

    public Solution(int temperature) {
        this.currentDate = new Date();
        this.temperature = temperature;

        string = "Today is %s, and the current temperature is %s C";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.string = String.format(string, format.format(currentDate), temperature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return string == solution.string;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, currentDate, temperature, string);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
