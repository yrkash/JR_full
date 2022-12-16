package com.javarush.task.task39.task3913_LogParser_StreamAPI_reflection;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.javarush.task.task39.task3913_LogParser_StreamAPI_reflection.LogParser.*;

public class LogParserTest extends TestCase {

    LogParser parser;
    @Before
    public void setUp() throws Exception {
         parser = new LogParser(Paths.get("c:/temp/"));
    }

    @Test
    public void testGetRecordsByRangeOfDates() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
            List<Log> logList = getLogList(getFileTree(Paths.get("c:/temp/")));
            List<Log> actual = parser.getRecordsByRangeOfDates
                    (format.parse("30.08.2012 16:08:40"),
                    format.parse("11.12.2013 10:11:12"));
            Log log = new Log("146.34.15.5",
                    "Eduard Petrovich Morozko",
                    format.parse("13.09.2013 5:04:50"),
                    Event.DOWNLOAD_PLUGIN,
                    Status.OK);
            List<Log> expected = new ArrayList<>();
            expected.add(log);
            Assert.assertEquals(expected, actual);
//146.34.15.5	Eduard Petrovich Morozko	13.09.2013 5:04:50	DOWNLOAD_PLUGIN	OK
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGetResultSet() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        try {
            List<Log> logList = parser.getRecordsByRangeOfDates
                    (format.parse("30.08.2012 16:08:40"),
                     format.parse("11.12.2013 10:11:12"));
            Set<Object> actualSet = parser.getResultSet("user", logList);
            String actual = (String) actualSet.iterator().next();
            String expected = new String("Eduard Petrovich Morozko");
            Assert.assertEquals(expected, actual);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}