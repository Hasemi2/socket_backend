package com.chat.realtime.web.dto;

import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserListResponseDto {

    private String dataType;
    private List<User> data;

    @Builder
    public UserListResponseDto(String dataType, List<User> users) {
        this.dataType = dataType;
        this.data = users;
    }
}
