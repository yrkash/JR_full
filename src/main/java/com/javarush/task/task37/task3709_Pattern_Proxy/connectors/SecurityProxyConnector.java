package com.javarush.task.task37.task3709_Pattern_Proxy.connectors;

import com.javarush.task.task37.task3709_Pattern_Proxy.security.SecurityChecker;
import com.javarush.task.task37.task3709_Pattern_Proxy.security.SecurityCheckerImpl;

public class SecurityProxyConnector implements Connector {

    private SimpleConnector simpleConnector;
    private SecurityChecker securityChecker = new SecurityCheckerImpl();
    public SecurityProxyConnector(String s) {
        this.simpleConnector = new SimpleConnector(s);
    }

    @Override
    public void connect() {
        if (securityChecker.performSecurityCheck()) simpleConnector.connect();
    }
}
