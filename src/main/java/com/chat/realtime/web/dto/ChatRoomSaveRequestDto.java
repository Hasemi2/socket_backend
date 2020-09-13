package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomSaveRequestDto {

    private String receiver;
    private String userToken;

    @Builder
    public ChatRoomSaveRequestDto(String receiver, String userToken) {
        this.receiver = receiver;
        this.userToken = userToken;
    }

}
