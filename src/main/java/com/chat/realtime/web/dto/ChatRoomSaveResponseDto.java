package com.chat.realtime.web.dto;

import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class ChatRoomSaveResponseDto {

    private String dataType;

    private Map<String, Object> data;

    @Builder
    public ChatRoomSaveResponseDto(String dataType, Long roomId, String myId, String receiver ) {
        this.dataType = dataType;
        this.data = new HashMap<>();
        data.put("roomId" , roomId);
        data.put("myId" , myId);
        data.put("receiver" , receiver);
    }
}
