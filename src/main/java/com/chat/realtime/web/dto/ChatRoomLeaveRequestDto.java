package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomLeaveRequestDto {

    private String roomId;
    private String userToken;

    @Builder
    public ChatRoomLeaveRequestDto(String roomId, String userToken) {
        this.roomId = roomId;
        this.userToken = userToken;
    }

}
