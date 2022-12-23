package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private Provider[] providers;
    public Controller(Provider ... providers) {
        if (providers.length == 0) throw new IllegalArgumentException();
        this.providers = providers;
    }

    @Override
    public String toString() {
        return "Controller{" +
                "providers=" + Arrays.toString(providers) +
                '}';
    }

    public void scan() {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider : providers) {
            vacancies.addAll(provider.getJavaVacancies("moscow"));
        }

        for (int i = 0; i < vacancies.size(); i++) {
            System.out.println(vacancies.get(i).getTitle());
            System.out.println(vacancies.get(i).getSalary());
            System.out.println(vacancies.get(i).getCity());
            System.out.println(vacancies.get(i).getCompanyName());
            System.out.println(vacancies.get(i).getSiteName());
            System.out.println(vacancies.get(i).getUrl());
            System.out.println("");
        }
    }
}
