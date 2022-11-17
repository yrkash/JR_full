package com.javarush.task.task33.task3307;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.StringReader;

/*
Десериализация XML объекта
*/

public class Solution {
    public static void main(String[] args) throws IOException, JAXBException {
        String xmlData = "<cat><name>Murka</name><age>5</age><weight>4</weight></cat>";
        Cat cat = convertFromXmlToNormal(xmlData, Cat.class);
        System.out.println(cat);
    }

    public static <T> T convertFromXmlToNormal(String xmlData, Class<T> clazz) throws IOException, JAXBException {

        StringReader reader = new StringReader(xmlData);

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        T instance = (T) unmarshaller.unmarshal(reader);

        return instance;
    }

    @XmlType(name = "cat")
    @XmlRootElement
    public static class Cat {
        public String name;
        public int age;
        public int weight;

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", weight=" + weight +
                    '}';
        }
    }
}
