package com.chat.realtime.web.controller;

import com.chat.realtime.service.UserService;
import com.chat.realtime.web.connect.TokenMapper;
import com.chat.realtime.web.dto.UserListResponseDto;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor //final이 붙거나 @Notnull 이 붙은 필드의 생성자를 자동생성
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserSaveResponseDto login(@RequestBody UserSaveRequestDto requestDto) {
        UserSaveResponseDto responseDto = userService.login(requestDto);
        TokenMapper.set(responseDto.getConnectToken(), responseDto.getAuthToken());
        return responseDto;
    }

    @MessageMapping("/userList/get")
    @SendTo("/topic/user")
    public UserListResponseDto userList(SimpMessageHeaderAccessor headerAccessor) {
        return userService.findCurrentUserList();
    }
}
