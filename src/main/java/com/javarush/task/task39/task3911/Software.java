package com.javarush.task.task39.task3911;

import java.util.*;

public class Software {
    private int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        if (!getVersionHistoryMap().containsKey(rollbackVersion)) return false;
        int curKey;
        Set <Integer> deletedVersions = new LinkedHashSet<>();
        if (currentVersion > rollbackVersion)  {
            currentVersion = rollbackVersion;
            Iterator<Map.Entry<Integer,String>> iterator = versionHistoryMap.entrySet().iterator();
            while (iterator.hasNext()) {
                curKey = iterator.next().getKey();
                if ( curKey > rollbackVersion) {
                    deletedVersions.add(curKey);
                }
            }
        }
        for (int version : deletedVersions) {
            versionHistoryMap.remove(version);
        }
        return true;
    }
}
