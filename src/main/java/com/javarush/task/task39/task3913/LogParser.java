package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.EventQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery {

    private Path path;

    public LogParser(Path path) {
        this.path = path;
    }

    public List<Log> getRecordsByRangeOfDates(Date after, Date before) {
        try {
            List<Log> logList = getLogList(getFileTree(path));
            return logList.stream()
                    .filter(isDateInRange(after,before))
                    .collect(Collectors.toList());
        } catch (IOException ignore) {
        }
        return null;
    }
    public static Predicate<Log> isDateInRange(Date after, Date before) {
        return t-> ((after == null || !t.getDate().before(after)) && (before == null || !t.getDate().after(before)));
    }
    public static  List<Log> getLogList(List<String> fileList) {
        List<Log> logList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        for(String file: fileList) {
            String str = new String();
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                while ((str = bufferedReader.readLine()) != null) {
                    Log log;
                    String[] buff = str.split("\t");
                    String ip = buff[0];
                    String name = buff[1];
                    Date date = format.parse(buff[2]);
                    Status status = Status.valueOf(buff[4]);
                    if (buff[3].contains(" ")) {
                        Event event = Event.valueOf(buff[3].split(" ")[0]);
                        int taskNumber = Integer.parseInt(buff[3].split(" ")[1]);
                        log = new Log(ip, name, date, event, taskNumber, status);
                    } else {
                        Event event = Event.valueOf(buff[3]);
                        log = new Log(ip, name, date, event, status);
                    }
                    logList.add(log);
                }
            } catch (ParseException e) {

            } catch (IOException e) {

            }
        }
        return logList;
    }
    public static List<String> getFileTree(Path path) throws IOException {
        List<String> result = new ArrayList<>();

        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(Paths.get(path.toString()), options, 20, new LogParser.GetFiles(result));

        return result;
    }
    private static class GetFiles extends SimpleFileVisitor<Path> {
        private List<String> result;

        public GetFiles(List<String> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            if (Files.isRegularFile(path) && (path.toString().endsWith(".log"))) {
                result.add(path.toAbsolutePath().toString());

            }
            return super.visitFile(path, basicFileAttributes);
        }
    }

    //EventQuery methods
    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getEvent)
                .collect(Collectors.toSet()).size();
    }
    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getEvent)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getIp().equals(ip))
                .map(Log::getEvent)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getName().equals(user))
                .map(Log::getEvent)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getStatus().equals(Status.FAILED))
                .map(Log::getEvent)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getStatus().equals(Status.ERROR))
                .map(Log::getEvent)
                .collect(Collectors.toSet());
    }
    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getTaskNumber() == task)
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .map(Log::getEvent)
                .collect(Collectors.toList()).size();
    }
    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getTaskNumber() == task)
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .map(Log::getEvent)
                .collect(Collectors.toList()).size();
    }
    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .collect(Collectors.toMap(Log::getTaskNumber, t -> 1, Integer::sum));
    }
    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .collect(Collectors.toMap(Log::getTaskNumber, t -> 1, Integer::sum));
    }

    //DateQuery methods
    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getName().equals(user))
                .filter(t->t.getEvent().equals(event))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getStatus().equals(Status.FAILED))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getStatus().equals(Status.ERROR))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Optional<Date> optionalDate = getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getName().equals(user))
                .filter(t->t.getEvent().equals(Event.LOGIN))
                .map(Log::getDate)
                .sorted()
                .findFirst();
        return (optionalDate.isPresent()) ? optionalDate.get() : null;
    }
    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Optional<Date> optionalDate = getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getName().equals(user))
                .filter(t->t.getEvent().equals(Event.SOLVE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getDate)
                .sorted()
                .findFirst();
        return (optionalDate.isPresent()) ? optionalDate.get() : null;
    }
    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Optional<Date> optionalDate = getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getName().equals(user))
                .filter(t->t.getEvent().equals(Event.DONE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getDate)
                .sorted()
                .findFirst();
        return (optionalDate.isPresent()) ? optionalDate.get() : null;
    }
    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getName().equals(user))
                .filter(t->t.getEvent().equals(Event.WRITE_MESSAGE))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getName().equals(user))
                .filter(t->t.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }

    //UserQuery methods
    @Override
    public Set<String> getAllUsers() {
        return getRecordsByRangeOfDates(null, null).stream()
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> userSet = getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getName)
                .collect(Collectors.toSet());
        return userSet.size();
    }
    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getName().equals(user))
                .map(Log::getEvent)
                .collect(Collectors.toSet())
                .size();
    }
    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getIp().equals(ip))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.LOGIN))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.WRITE_MESSAGE))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .map(Log::getName)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getName)
                .collect(Collectors.toSet());
    }

    //IPQuery methods
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        Set<String> IPSet = getRecordsByRangeOfDates(after,before).stream()
                .map(v-> v.getIp())
                .collect(Collectors.toSet());
        return IPSet.size();
    }
    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return getRecordsByRangeOfDates(after,before).stream()
                .map(v-> v.getIp())
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after,before).stream()
                .filter(v->v.getName().equals(user))
                .map(v-> v.getIp())
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return getRecordsByRangeOfDates(after,before).stream()
                .filter(v->v.getEvent().equals(event))
                .map(v-> v.getIp())
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return getRecordsByRangeOfDates(after,before).stream()
                .filter(v->v.getStatus().equals(status))
                .map(v-> v.getIp())
                .collect(Collectors.toSet());
    }
}