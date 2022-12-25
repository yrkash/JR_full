package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View{

    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("[.]", "/") + "/vacancies.html";
    private Controller controller;
    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String updatedFileName = getUpdatedFileContent(vacancies);
            updateFile(updatedFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(vacancies.size());
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("moscow");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        return null;
    }
    private void updateFile(String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
