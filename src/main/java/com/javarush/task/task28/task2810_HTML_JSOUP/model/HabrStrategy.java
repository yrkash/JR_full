package com.javarush.task.task28.task2810_HTML_JSOUP.model;

import com.javarush.task.task28.task2810_HTML_JSOUP.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class HabrStrategy implements Strategy {

    private static final String URL_FORMAT = "https://career.habr.com/vacancies?page=%d&q=java+%s&type=all";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> result = new ArrayList<>();
        try {
            int pageNumber = 0;

            try {
                do {
                    Document document = getDocument(searchString, pageNumber);
                    Elements elements = document.getElementsByClass("vacancy-card__info");
                    if  (elements.size() == 0) break;
                for (Element element: elements) {
                    Elements links = element.getElementsByClass("vacancy-card__title-link");
                    String url = "https://career.habr.com" + links.get(0).attr("href");
                    String salary = element.getElementsByClass("basic-salary").text();
                    String title = links.get(0).text();
                    String city = element.getElementsByClass("vacancy-card__meta").get(0).text();
                    String companyName = element.getElementsByClass("vacancy-card__company-title").get(0).text();
                    String siteName = "habr.ru";
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(url);
                    vacancy.setSalary(salary);
                    vacancy.setTitle(title);
                    vacancy.setCity(city);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName(siteName);
                    result.add(vacancy);
                }
                    System.out.println(pageNumber);
                    pageNumber++;

                }  while (true);
            } catch (SocketTimeoutException ignore) {
                System.out.println(ignore.getMessage());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT,page,searchString))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .referrer("career.habr.com")
                .get();


    }
}
