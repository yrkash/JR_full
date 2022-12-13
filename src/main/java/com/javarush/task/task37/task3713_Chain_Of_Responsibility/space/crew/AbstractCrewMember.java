package com.javarush.task.task37.task3713_Chain_Of_Responsibility.space.crew;

public abstract class AbstractCrewMember {
    public enum CompetencyLevel {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    protected CompetencyLevel competencyLevel;

    protected AbstractCrewMember nextCrewMember;

    public void setNextCrewMember(AbstractCrewMember nextCrewMember) {
        this.nextCrewMember = nextCrewMember;
    }

    public void handleRequest(CompetencyLevel competencyLevel, String request) {
        if (this.competencyLevel.ordinal() < competencyLevel.ordinal()) {
            nextCrewMember.handleRequest(competencyLevel,request);
        } else {
            doTheJob(request);
        }
    }

    protected abstract void doTheJob(String request);
}
