package com.chat.realtime.web.dto;

import com.chat.realtime.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {

    private String userToken;

    public UserSaveResponseDto(User user) {
        this.userToken = user.getUserToken();
    }
}
