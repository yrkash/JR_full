package com.javarush.task.task34.task3409_Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/*
Настраиваем логгер
*/

public class Solution {
    private static final Logger logger = Logger.getLogger(Solution.class);


    public static void main(String args[]) throws IOException {
        String logProperties = "src/main/java/" +
                Solution.class.getPackage().getName().replaceAll("[.]", "/")
                + "/log4j.properties";
        Path path = Paths.get(logProperties).toAbsolutePath();
        try (InputStream is = new FileInputStream(path.toFile())) {
            Properties properties = new Properties();
            properties.load(is);
            PropertyConfigurator.configure(properties);
//            logger.debug("properties load from" + logProperties);
            logger.error("Hello error");
//            logger.warn("Warn warn!");
//            logger.info("Information");
        }
    }


}
