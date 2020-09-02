package com.chat.realtime.web.controller;

import com.chat.realtime.service.UserService;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor //final이 붙거나 @Notnull 이 붙은 필드의 생성자를 자동생성
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserSaveResponseDto login(@RequestBody UserSaveRequestDto requestDto) throws Exception {
        return userService.login(requestDto);
    }
}
