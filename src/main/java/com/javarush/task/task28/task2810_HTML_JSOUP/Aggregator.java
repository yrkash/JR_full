package com.javarush.task.task28.task2810_HTML_JSOUP;

import com.javarush.task.task28.task2810_HTML_JSOUP.model.HHStrategy;
import com.javarush.task.task28.task2810_HTML_JSOUP.model.Model;
import com.javarush.task.task28.task2810_HTML_JSOUP.model.Provider;
import com.javarush.task.task28.task2810_HTML_JSOUP.view.HtmlView;

public class Aggregator {

    public static void main(String[] args) {
        HtmlView view = new HtmlView();

        Model model = new Model(view, new Provider(new HHStrategy()));
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
