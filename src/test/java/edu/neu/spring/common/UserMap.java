package edu.neu.spring.common;

import java.util.HashMap;

public class UserMap extends HashMap<String, String> {
    private static final HashMap<String, String> hashMap = new HashMap<>();

    static {
        addItem("0", "a");
        addItem("1", "b");
        addItem("2", "c");
    }

    public static void addItem(String id, String name) {
        hashMap.put(id, name);
    }

    public static void deleteItem(String id) {
        hashMap.remove(id);
    }

    public static String getItem(String id) {
        return hashMap.get(id);
    }
}
