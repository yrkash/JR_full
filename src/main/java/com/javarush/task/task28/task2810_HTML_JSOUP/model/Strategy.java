package com.javarush.task.task28.task2810_HTML_JSOUP.model;

import com.javarush.task.task28.task2810_HTML_JSOUP.vo.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String searchString);

}
