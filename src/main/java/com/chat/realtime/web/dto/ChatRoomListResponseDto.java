package com.chat.realtime.web.dto;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ChatRoomListResponseDto {

    private String dataType;
    private List<ChatRoom> data;

    @Builder
    public ChatRoomListResponseDto(String dataType, List<ChatRoom> rooms) {
        this.dataType = dataType;
        this.data = rooms;
    }
}
