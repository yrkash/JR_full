package com.javarush.task.task39.task3913_LogParser_StreamAPI_reflection;

import java.util.Date;
import java.util.Objects;

public class Log {
    private String ip;
    private String user;
    private Date date;
    private Event event;

    private int taskNumber;
    private Status status;

    public Log(String ip, String user, Date date, Event event, int taskNumber, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        this.taskNumber = taskNumber;
        this.status = status;
    }

    public Log(String ip, String user, Date date, Event event, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        this.taskNumber = 0;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTaskNumber() { return taskNumber; }

    public void setTaskNumber(int taskNumber) { this.taskNumber = taskNumber;}

    @Override
    public String toString() {
        return "Log{" +
                "ip='" + ip + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", event=" + event +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return taskNumber == log.taskNumber && Objects.equals(ip, log.ip) && Objects.equals(user, log.user) && Objects.equals(date, log.date) && event == log.event && status == log.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, user, date, event, taskNumber, status);
    }
}
