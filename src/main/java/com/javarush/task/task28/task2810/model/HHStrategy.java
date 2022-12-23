package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {

        try {
            Document document = Jsoup.connect(String.format(URL_FORMAT, "moscow", 2)).get();
            Elements links = document.select("a[href]");
            Elements media = document.select("[src]");
            Elements imports = document.select("link[href]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
