package com.chat.realtime.web.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DataType {

    CONNECTED_USER_LIST("CONNECTED_USER_LIST"),
    GET_ROOM_LIST("GET_ROOM_LIST"),
    ADD_ROOM("ADD_ROOM"),
    LEAVE_ROOM("LEAVE_ROOM"),
    CHAT_MESSAGE("CHAT_MESSAGE"),
    SESSION_VALUE("SESSION_VALUE");

    private final String dataType;
}
