package com.chat.realtime.web.dto;

import com.chat.realtime.domain.room.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomSaveRequestDto {

    private String roomName;

    @Builder
    public ChatRoomSaveRequestDto(String roomName) {
        this.roomName = roomName;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomName(roomName)
                .build();
    }
}
