package com.chat.realtime.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSessionResponseDto {

    private String dataType;
    private UserSessionResponseDto.Data data;

    @Builder
    public UserSessionResponseDto(String dataType, UserSessionResponseDto.Data data) {
        this.dataType = dataType;
        this.data = data;
    }

    @NoArgsConstructor
    @Getter
    public static class Data {

        private String sessionId;


        @Builder
        public Data(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
