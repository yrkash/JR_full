package com.javarush.task.task39.task3913;

import com.javarush.task.task31.task3102_walkFileTree.Solution;
import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery{

    private Path path;

    public LogParser(Path path) {
        this.path = path;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        try {
            System.out.println(getFileTree(path.toString()));
        } catch (IOException ignore) {
        }
        return 0;
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {

        return null;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return null;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return null;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return null;
    }

    public static List<String> getFileTree(String root) throws IOException {
        List<String> result = new ArrayList<>();

        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(Paths.get(root), options, 20, new LogParser.GetFiles(result));

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