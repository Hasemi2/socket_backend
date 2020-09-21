package com.chat.realtime.web.dto;

import com.chat.realtime.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {

    private String authToken;

    private String connectToken;

    public UserSaveResponseDto(User user, String connectToken) {
        this.authToken = user.getUserToken();
        this.connectToken = connectToken;
    }
}
