package com.simple.main;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<Record2, String> map = new HashMap<>();
        map.put(new Record2(2, "name"), "value");

        final int num = 2;
        System.out.println(map.get(new Record2(num, "name")));

        new Record2(null, "");
    }

    public record Record2(int value, String name) {}
}
