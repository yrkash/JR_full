package com.javarush.task.task23.task2309_MockFakeDB;

import com.javarush.task.task23.task2309_MockFakeDB.vo.*;

import java.util.List;

/*
Анонимность иногда так приятна!
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        print(solution.getUsers());
        print(solution.getLocations());
    }

    public List<User> getUsers() {
        AbstractDbSelectExecutor<User> userExecutor = new AbstractDbSelectExecutor<User>() {
            @Override
            public String getQuery() {
                return "SELECT * FROM USER";
            }
        };

        return userExecutor.execute();
    }

    public List<Location> getLocations() {
        AbstractDbSelectExecutor<Location> locationExecutor = new AbstractDbSelectExecutor<Location>() {
            @Override
            public String getQuery() {
                return "SELECT * FROM LOCATION";
            }
        };
        return locationExecutor.execute();
    }

    public List<Server> getServers() {
        AbstractDbSelectExecutor<Server> serverExecutor = new AbstractDbSelectExecutor<Server>() {
            @Override
            public String getQuery() {
                return "SELECT * FROM SERVER";
            }
        };
        return serverExecutor.execute();
    }

    public List<Subject> getSubjects() {
        AbstractDbSelectExecutor<Subject> subjectExecutor = new AbstractDbSelectExecutor<Subject>() {
            @Override
            public String getQuery() {
                return "SELECT * FROM SUBJECT";
            }
        };
        return subjectExecutor.execute();
    }

    public List<Subscription> getSubscriptions() {
        AbstractDbSelectExecutor<Subscription> subscriptionExecutor = new AbstractDbSelectExecutor<Subscription>() {
            @Override
            public String getQuery() {
                return "SELECT * FROM SUBSCRIPTION";
            }
        };
        return subscriptionExecutor.execute();
    }

    public static void print(List list) {
        String format = "Id=%d, name='%s', description=%s";
        for (Object obj : list) {
            NamedItem item = (NamedItem) obj;
            System.out.println(String.format(format, item.getId(), item.getName(), item.getDescription()));
        }
    }
}
