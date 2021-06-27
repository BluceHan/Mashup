package com.flinker.utils;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Accounts {

    private static final Set<String> synHashSet = Collections.synchronizedSet(new HashSet<String>());

    private Accounts() {
    }

    public static boolean contains(String account) {
        return synHashSet.contains(account);
    }

    public static boolean add(String account) {
        return synHashSet.add(account);
    }

    public static boolean remove(String account) {
        return synHashSet.remove(account);
    }
}
