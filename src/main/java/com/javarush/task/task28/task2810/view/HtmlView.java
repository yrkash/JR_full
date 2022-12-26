package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View{

//    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("[.]", "/") + "/vacancies.html";
    private final String filePath = "c:/temp/vacancies.html";
    private Controller controller;
    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String content = getUpdatedFileContent(vacancies);
            updateFile(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Нижний Новгород");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        try {
            Document doc = getDocument();
            Elements templateHidden = doc.getElementsByClass("template");
            Element template = templateHidden.clone().removeAttr("style").removeClass("template").get(0);
            Elements removableElements = doc.getElementsByClass("vacancy");
            for (Element element: removableElements) {
                if (!element.hasClass("template")) {
                    element.remove();
                }
            }
            for (Vacancy vacancy: vacancyList) {
                Element currentElement = template.clone();
                Element city = currentElement.getElementsByClass("city").get(0);
                city.appendText(vacancy.getCity());
                Element companyName = currentElement.getElementsByClass("companyName").get(0);
                companyName.appendText(vacancy.getCompanyName());
                Element salary = currentElement.getElementsByClass("salary").get(0);
                salary.appendText(vacancy.getSalary());
                Element vacancyLink = currentElement.getElementsByAttribute("href").get(0);
                vacancyLink.appendText(vacancy.getTitle());
                vacancyLink.attr("href", vacancy.getUrl());
                templateHidden.before(currentElement.outerHtml());
            }
            return doc.html();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }
    private void updateFile(String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected Document getDocument() throws IOException {
        Document document = Jsoup.parse(new File(filePath),"UTF-8");
        return document;
    }

}
