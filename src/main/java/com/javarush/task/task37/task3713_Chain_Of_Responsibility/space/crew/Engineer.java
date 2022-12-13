package com.javarush.task.task37.task3713_Chain_Of_Responsibility.space.crew;

public class Engineer extends AbstractCrewMember {
    public Engineer(AbstractCrewMember.CompetencyLevel competencyLevel) {
        this.competencyLevel = competencyLevel;
    }

    protected void doTheJob(String request) {
        System.out.println(request + " is a common engineering task. To the work!");
    }
}
