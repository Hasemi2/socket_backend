package com.chat.realtime.web.connect;

import java.util.HashMap;
import java.util.Map;

public class SocketConnection {

    private static Map<String, String> instance;

    private SocketConnection() {
    }

    public static Map<String, String> getInstance() {
        if (instance == null) {
            return instance = new HashMap<>();
        }
        return instance;
    }
}
