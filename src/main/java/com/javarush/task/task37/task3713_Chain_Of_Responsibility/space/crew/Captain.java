package com.javarush.task.task37.task3713_Chain_Of_Responsibility.space.crew;

public class Captain extends AbstractCrewMember {
    public Captain(AbstractCrewMember.CompetencyLevel competencyLevel) {
        this.competencyLevel = competencyLevel;
    }

    protected void doTheJob(String request) {
        System.out.println("Only the captain can handle request " + request + ". Let's do this!");
    }
}
