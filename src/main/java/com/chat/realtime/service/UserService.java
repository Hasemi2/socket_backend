package com.chat.realtime.service;

import com.chat.realtime.domain.user.User;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import com.chat.realtime.web.util.JwtUtil;
import com.chat.realtime.web.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final SHA256Util sha256Util;

    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponseDto login(UserSaveRequestDto requestDto) throws Exception {

        String userId = requestDto.getUserId();
        String password = sha256Util.getEncrypt(requestDto.getPassword());
        String newToken = jwtUtil.createToken(userId);

        Optional<User> user = userRepository.findByUserId(userId);

        user.ifPresent(dbUser -> {
            userRepository.findByUserIdAndPassword(userId, password).orElseThrow(() -> new IllegalArgumentException("비밀번호를 확인해주세요."));
        });

        User loginUser = userRepository.findByUserIdAndPassword(userId, password).
                orElseGet(
                        () -> userRepository.save(
                                UserSaveRequestDto.builder()
                                        .userId(userId)
                                        .password(password)
                                        .userToken(newToken)
                                        .tokenExpiredTime(jwtUtil.getExpiredTime(newToken))
                                        .build().toEntity())
                ).update(newToken, jwtUtil.getExpiredTime(newToken));

        return new UserSaveResponseDto(loginUser);
    }
}
