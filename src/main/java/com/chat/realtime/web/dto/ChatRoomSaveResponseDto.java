package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomSaveResponseDto {

    private String dataType;

    private Data data;

    @Builder
    public ChatRoomSaveResponseDto(String dataType, Data data) {
        this.dataType = dataType;
        this.data = data;
    }

    @NoArgsConstructor
    @Getter
    public static class Data {

        private Long roomId;

        private String myId;

        private String receiver;

        @Builder
        public Data(Long roomId, String myId, String receiver) {
            this.roomId = roomId;
            this.myId = myId;
            this.receiver = receiver;
        }
    }

}
