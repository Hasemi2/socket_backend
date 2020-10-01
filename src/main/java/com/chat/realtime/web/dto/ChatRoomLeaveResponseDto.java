package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomLeaveResponseDto {

    private String dataType;
    private ChatRoomLeaveResponseDto.Data data;

    @Builder
    public ChatRoomLeaveResponseDto(String dataType, ChatRoomLeaveResponseDto.Data data) {
        this.dataType = dataType;
        this.data = data;
    }

    @NoArgsConstructor
    @Getter
    public static class Data {

        private Long roomId;
        private String leaverId;

        @Builder
        public Data(Long roomId, String leaverId) {
            this.roomId = roomId;
            this.leaverId = leaverId;
        }
    }
}
