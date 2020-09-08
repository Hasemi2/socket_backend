package com.chat.realtime.service;

import com.chat.realtime.domain.user.User;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.UserListResponseDto;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import com.chat.realtime.web.dto.type.DataType;
import com.chat.realtime.web.exception.CommonException;
import com.chat.realtime.web.util.JwtUtil;
import com.chat.realtime.web.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final SHA256Util sha256Util;

    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponseDto login(UserSaveRequestDto requestDto) {

        String userId = requestDto.getUserId();
        String password;

        try {
            password = sha256Util.getEncrypt(requestDto.getPw());
        } catch (NoSuchAlgorithmException ex) {
            throw new CommonException(500, "비밀번호 암호화 중 에러 발생");
        }

        String newToken = jwtUtil.createToken(userId);

        Optional<User> loginUser = userRepository.findByUserIdAndPassword(userId, password);
        if (loginUser.isPresent()) {
            loginUser.get().update(newToken, jwtUtil.getExpiredTime(newToken));
            return new UserSaveResponseDto(loginUser.get());
        } else if (isJoinedUser(userId)) {
            throw new CommonException(401, "비밀번호 확인");
        } else {
            return new UserSaveResponseDto(
                    userRepository.save(
                            UserSaveRequestDto.builder()
                                    .userId(userId)
                                    .password(password)
                                    .userToken(newToken)
                                    .tokenExpiredTime(jwtUtil.getExpiredTime(newToken))
                                    .build().toEntity())
            );
        }
    }

    public UserListResponseDto findAll() {
        return UserListResponseDto.builder()
                .dataType(DataType.CONNECTED_USER_LIST.getDataType())
                .users(userRepository.findAll())
                .build();
    }

    private boolean isJoinedUser(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }
}
