package com.dev.springwebsocket.utils;

import com.dev.springwebsocket.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils() {

    }

    public static Message getObject(final String message) throws Exception {
        return objectMapper.readValue(message, Message.class);
    }

    // Serialize : Object -> JSON
    public static String getString(final Message message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }

}
