package com.javarush.task.task27.task2712_Restaurant.statistic.event;

import java.util.Date;

public interface EventDataRow {

    public EventType getType();

    public Date getDate();

    public int getTime();

    void setCurrentDate(Date date);
}
