package com.chat.realtime.web.dto;

import com.chat.realtime.domain.chat.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatSaveResponseDto {

    private String dataType;
    private Chat data;

    @Builder
    public ChatSaveResponseDto(String dataType, Chat chat) {
        this.dataType = dataType;
        this.data = chat;
    }
}
