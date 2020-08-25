package com.example.dto;

import com.example.socket.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChatRoomSaveRequestDto {

    private String roomName;

    @Builder
    public ChatRoomSaveRequestDto(String roomName) {
        this.roomName = roomName;
    }

//    public ChatRoom toEntity() {
//        return ChatRoom.builder()
//                .roomName(roomName)
//                .build();
//    }
}
