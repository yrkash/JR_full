package com.javarush.task.task37.task3709_Pattern_Proxy;

import com.javarush.task.task37.task3709_Pattern_Proxy.connectors.Connector;
import com.javarush.task.task37.task3709_Pattern_Proxy.connectors.SecurityProxyConnector;
import com.javarush.task.task37.task3709_Pattern_Proxy.connectors.SimpleConnector;

/*
Security Proxy
*/

public class Solution {
    public static void main(String[] args) {
        Connector securityProxyConnector = new SecurityProxyConnector("google.com");
        Connector simpleConnector = new SimpleConnector("javarush.ru");

        System.out.println("Connecting with SimpleConnector...");
        simpleConnector.connect();

        System.out.println("----------------------------------------------------");

        System.out.println("Connecting with SecurityProxyConnector...");
        securityProxyConnector.connect();
    }
}
