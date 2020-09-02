package com.chat.realtime.web.dto;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    private String userId;

    private String password;

    private String userToken;

    private LocalDateTime tokenExpiredTime;


    @Builder
    public UserSaveRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Builder
    public UserSaveRequestDto(String userId, String password, String userToken, LocalDateTime tokenExpiredTime) {
        this.userId = userId;
        this.password = password;
        this.userToken = userToken;
        this.tokenExpiredTime = tokenExpiredTime;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .userToken(userToken)
                .build();
    }
}
