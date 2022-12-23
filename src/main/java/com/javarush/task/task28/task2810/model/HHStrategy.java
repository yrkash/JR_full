package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> result = new ArrayList<>();
        try {
            int pageNumber = 0;
            do {
                Document document = getDocument(searchString, pageNumber);
//            Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                Elements elements = document.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy vacancy-serp__vacancy_standard_plus");
                if  (elements.size() == 0) break;
                for (Element element: elements) {
                    Elements links = element.getElementsByAttributeValue("class","serp-item__title");
//                    Elements links = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title");
                    String url = links.attr("href");
//                    String url = links.get(0).attr("href");
                    String salary = element.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-compensation").text();
                    String title = element.getElementsByAttributeValue("class","serp-item__title").text();
//                    String title = links.get(0).text();
                    String city = element.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-address").get(0).text();
                    String companyName = element.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy-employer").get(0).text();
                    String siteName = "hh.ru";
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(url);
                    vacancy.setSalary(salary);
                    vacancy.setTitle(title);
                    vacancy.setCity(city);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName(siteName);
                    result.add(vacancy);
                }
                pageNumber++;
            }  while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT,searchString,page))
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                                .referrer("nn.hh.ru")
                                .get();


    }
}
