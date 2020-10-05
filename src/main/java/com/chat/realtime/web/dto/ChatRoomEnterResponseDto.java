package com.chat.realtime.web.dto;

import com.chat.realtime.domain.chat.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomEnterResponseDto {

    private String dataType;
    private List<Chat> data;

    @Builder
    public ChatRoomEnterResponseDto(String dataType, List<Chat> data) {
        this.dataType = dataType;
        this.data = data;
    }

}
