package com.javarush.task.task40.task4011;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
Свойства URL
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        decodeURLString("https://www.amrood.com/index.htm?language=en#j2se");
    }

    public static void decodeURLString(String s) throws MalformedURLException {
        try {
            URL aURL = new URL(s);
            System.out.println("protocol = " + aURL.getProtocol());
            System.out.println("authority = " + aURL.getAuthority());
            System.out.println("host = " + aURL.getHost());
            System.out.println("path = " + aURL.getPath());
            System.out.println("port = " + aURL.getPort());
            System.out.println("default port = " + aURL.getDefaultPort());
            System.out.println("query = " + aURL.getQuery());
            System.out.println("filename = " + aURL.getFile());
            System.out.println("ref = " + aURL.getRef());
        } catch (MalformedURLException e) {
            System.out.printf("Parameter %s is not a valid URL.",s);
        }

    }
}