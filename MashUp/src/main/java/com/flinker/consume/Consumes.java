package com.flinker.consume;

import java.util.*;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/23
 * @company:
 */
public class Consumes {
    private static final Map<String,String> synHashMap = Collections.synchronizedMap(new HashMap<String, String>());

    public Consumes() {}

    public static String add(String account, String data) {
        return synHashMap.put(account, data);
    }

    public static String get(String account) {
        return synHashMap.remove(account);
    }

    public static boolean contains(String account) {
        return synHashMap.containsKey(account);
    }
}
