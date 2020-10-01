package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomLeaveRequestDto {

    private Long roomId;
    private String userToken;

    @Builder
    public ChatRoomLeaveRequestDto(Long roomId, String userToken) {
        this.roomId = roomId;
        this.userToken = userToken;
    }

}
