package com.javarush.task.task28.task2810_HTML_JSOUP.view;

import com.javarush.task.task28.task2810_HTML_JSOUP.Controller;
import com.javarush.task.task28.task2810_HTML_JSOUP.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
