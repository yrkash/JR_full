package com.javarush.task.task31.task3109;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/*
Читаем конфиги
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Properties properties = solution.getProperties("c:/Users/y.mitryasov/IdeaProjects/JR_lvl31/src/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("c:/Users/y.mitryasov/IdeaProjects/JR_lvl31/src/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/notExists");
        properties.list(System.out);
    }

    public Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try(InputStream inputStream = new FileInputStream(fileName)) {
            if (fileName.endsWith("xml")) {
                properties.loadFromXML(inputStream);
            } else {
                properties.load(inputStream);
            }
            String name = properties.getProperty("name");
            String level = properties.getProperty("level");

        }  catch (IOException e) {
            return properties;
        }
        return properties;
    }
}
