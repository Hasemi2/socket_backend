package com.chat.realtime.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ChatRoomSaveRequestDto {

    private String receiver;
    private String userToken;

    @Builder
    public ChatRoomSaveRequestDto(String receiver) {
        this.receiver = receiver;
    }

}
