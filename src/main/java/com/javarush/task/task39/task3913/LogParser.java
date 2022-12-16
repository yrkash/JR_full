package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

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
        return t-> ((after == null || t.getDate().after(after)) && (before == null || t.getDate().before(before)));
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


    public Set<Object> getResultSet (String field1, List<Log> logList) {
        Method[] methods = Log.class.getDeclaredMethods();
        for (Method method: methods) {
            if (method.getName().toLowerCase().contains(field1)
                    && method.getName().toLowerCase().contains("get")) {
                Method finalValueGetter = method;
                finalValueGetter.setAccessible(true);
                return logList.stream()
                        .map(t-> {
                            try {
                                return finalValueGetter.invoke(t);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toSet());
            }
        }
        return null;
    }
    public List<Log> getRecordsWithValue (String field2, String value, Date date1, Date date2) {
        switch (field2) {
            case("ip"):
                return getRecordsByRangeOfDates(date1, date2).stream()
                    .filter(t-> t.getIp().equals(value))
                    .collect(Collectors.toList());
            case("user"):
                return getRecordsByRangeOfDates(date1, date2).stream()
                        .filter(t-> t.getUser().equals(value))
                        .collect(Collectors.toList());
            case("date"):
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
                Date date;
                try {
                    date = format.parse(value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return getRecordsByRangeOfDates(date1, date2).stream()
                        .filter(t-> t.getDate().equals(date))
                        .collect(Collectors.toList());
            case("status"):
                return getRecordsByRangeOfDates(date1, date2).stream()
                        .filter(t-> t.getStatus().equals(Status.valueOf(value)))
                        .collect(Collectors.toList());
            case("event"):
                return getRecordsByRangeOfDates(date1, date2).stream()
                        .filter(t-> t.getEvent().equals(Event.valueOf(value)))
                        .collect(Collectors.toList());
        }
        return null;
    }
    @Override
    public Set<Object> execute(String query) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        String field1;
        String field2 = null;
        String value1 = null;
        String value2 = null;
        String value3 = null;
        //Лист записей, где значение поля равно value
        List<Log> listWithValue;
        Pattern pattern = Pattern.compile("get (ip|user|date|event|status)"
                + "( for (ip|user|date|event|status) = \"(.*?)\")?"
                + "( and date between \"(.*?)\" and \"(.*?)\")?");
        Matcher matcher = pattern.matcher(query);
        matcher.find();
        field1 = matcher.group(1);
//        System.out.println("Gr1 = " + matcher.group(1));
//        System.out.println("Gr2 = " + matcher.group(2));
//        System.out.println("Gr3 = " + matcher.group(3));
//        System.out.println("Gr4 = " + matcher.group(4));
//        System.out.println("Gr5 = " + matcher.group(5));
//        System.out.println("Gr6 = " + matcher.group(6));
//        System.out.println("Gr7 = " + matcher.group(7));
        if (matcher.group(2) != null && matcher.group(5) != null) {
            field2 = matcher.group(3);
            value1 = matcher.group(4);

            value2 = matcher.group(6);
            value3 = matcher.group(7);
            Date date1;
            Date date2;
            try {
                date1 = format.parse(value2);
                date2 = format.parse(value3);
            } catch (ParseException e) {
                date1 = null;
                date2 = null;
            }
            listWithValue = getRecordsWithValue(field2, value1,date1,date2);
        } else {
            if (matcher.group(2) != null) {
                field2 = matcher.group(3);
                value1 = matcher.group(4);
                listWithValue = getRecordsWithValue(field2, value1,null,null);
            } else {
                listWithValue = getRecordsByRangeOfDates(null,null);
            }
        }

        return getResultSet(field1, listWithValue);
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
                .filter(t-> t.getUser().equals(user))
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
    public Set<Date> getAllDates(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getUser().equals(user))
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
                .filter(t->t.getUser().equals(user))
                .filter(t->t.getEvent().equals(Event.LOGIN))
                .map(Log::getDate)
                .sorted()
                .findFirst();
        return (optionalDate.isPresent()) ? optionalDate.get() : null;
    }
    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Optional<Date> optionalDate = getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getUser().equals(user))
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
                .filter(t->t.getUser().equals(user))
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
                .filter(t->t.getUser().equals(user))
                .filter(t->t.getEvent().equals(Event.WRITE_MESSAGE))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t->t.getUser().equals(user))
                .filter(t->t.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(Log::getDate)
                .collect(Collectors.toSet());
    }

    //UserQuery methods
    @Override
    public Set<String> getAllUsers() {
        return getRecordsByRangeOfDates(null, null).stream()
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> userSet = getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getUser)
                .collect(Collectors.toSet());
        return userSet.size();
    }
    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getUser().equals(user))
                .map(Log::getEvent)
                .collect(Collectors.toSet())
                .size();
    }
    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getIp().equals(ip))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.LOGIN))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.WRITE_MESSAGE))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.SOLVE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .map(Log::getUser)
                .collect(Collectors.toSet());
    }
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getRecordsByRangeOfDates(after, before).stream()
                .filter(t-> t.getEvent().equals(Event.DONE_TASK))
                .filter(t->t.getTaskNumber() == task)
                .map(Log::getUser)
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
                .filter(v->v.getUser().equals(user))
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

    public Set<Status> getAllStatus(Date after, Date before) {
        return getRecordsByRangeOfDates(after, before).stream()
                .map(Log::getStatus)
                .collect(Collectors.toSet());
    }
}