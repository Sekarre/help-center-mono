package com.sekarre.chatdemo.util;

import java.util.UUID;

public class RandomStringGenerator {

    public static String getRandomString() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
