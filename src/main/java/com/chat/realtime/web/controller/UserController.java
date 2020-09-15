package com.chat.realtime.web.controller;

import com.chat.realtime.service.UserService;
import com.chat.realtime.web.dto.UserListResponseDto;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import com.chat.realtime.web.dto.UserSessionResponseDto;
import com.chat.realtime.web.dto.type.DataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor //final이 붙거나 @Notnull 이 붙은 필드의 생성자를 자동생성
@RestController
public class UserController {

    private final SimpUserRegistry simpUserRegistry;

    private final UserService userService;

    @PostMapping("/login")
    public UserSaveResponseDto login(@RequestBody UserSaveRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @MessageMapping("/sessionId/get")
    @SendTo("/topic/user")
    public UserSessionResponseDto getSessionId(SimpMessageHeaderAccessor headerAccessor) {

        String sessionId = headerAccessor.getSessionId(); // Session ID
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        // TODO: 2020-09-15 임시 
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        sessionAttributes.put("sessionId", sessionId);
        sessionAttributes.put("token", token);

        return UserSessionResponseDto.builder()
                .dataType(DataType.SESSION_VALUE.getDataType())
                .data(UserSessionResponseDto
                        .Data.builder()
                        .sessionId(sessionId).build())
                .build();
    }

    @MessageMapping("/userList/get")
    @SendTo("/topic/user")
    public UserListResponseDto userList() {
        return userService.findAll();
    }
}
