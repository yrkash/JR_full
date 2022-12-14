package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

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

public class LogParser implements IPQuery{

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
}