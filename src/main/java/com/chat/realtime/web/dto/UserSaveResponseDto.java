package com.chat.realtime.web.dto;

import com.chat.realtime.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {

    private String authToken;

    public UserSaveResponseDto(User user) {
        this.authToken = user.getUserToken();
    }
}
