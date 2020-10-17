package com.chat.realtime.web.dto;

import com.chat.realtime.domain.chat.Chat;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatSaveRequestDto {

    private Long roomId;
    private String userToken;
    private String message;

    @Builder
    public ChatSaveRequestDto(String message) {
        this.message = message;
    }

    public Chat toEntity(User user, ChatRoom room, String chatContent) {
        return Chat.builder()
                .room(room)
                .user(user)
                .chatContent(chatContent)
                .build();
    }
}
