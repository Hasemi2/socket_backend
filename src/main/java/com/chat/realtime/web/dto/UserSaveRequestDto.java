package com.chat.realtime.web.dto;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    private String userId;

    private String pw;

    private String userToken;

    private LocalDateTime tokenExpiredTime;

    @Builder
    public UserSaveRequestDto(String userId, String password, String userToken, LocalDateTime tokenExpiredTime) {
        this.userId = userId;
        this.pw = password;
        this.userToken = userToken;
        this.tokenExpiredTime = tokenExpiredTime;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(pw)
                .userToken(userToken)
                .tokenExpiredTime(tokenExpiredTime)
                .build();
    }
}
