package com.chat.realtime.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Chat {

    private String name;
    private String message;

    @Builder
    public Chat(String name, String message) {
        this.name = name;
        this.message = message;
    }

}
