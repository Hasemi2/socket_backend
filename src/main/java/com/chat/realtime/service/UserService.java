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
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtUtil jwtUtil;

    private final SHA256Util sha256Util;

    private final UserRepository userRepository;

    private final SimpUserRegistry simpUserRegistry;

    @Transactional
    public UserSaveResponseDto login(UserSaveRequestDto requestDto) {

        String userId = requestDto.getUserId();
        String password;

        try {
            password = sha256Util.getEncrypt(requestDto.getPw());
        } catch (NoSuchAlgorithmException ex) {
            throw new CommonException(500, "비밀번호 암호화 중 에러 발생");
        }

        String newToken = jwtUtil.createUserAuthToken(userId);

        Optional<User> loginUser = userRepository.findByUserIdAndPassword(userId, password);
        if (loginUser.isPresent()) {
            loginUser.get().update(newToken, jwtUtil.getExpiredTime(newToken));
            String connectToken = jwtUtil.createConnectToken();
            return new UserSaveResponseDto(loginUser.get(), connectToken);
        } else if (isJoinedUser(userId)) {
            throw new CommonException(400 , "비밀번호 확인");
        } else {
            return new UserSaveResponseDto(
                    userRepository.save(
                            UserSaveRequestDto.builder()
                                    .userId(userId)
                                    .password(password)
                                    .userToken(newToken)
                                    .tokenExpiredTime(jwtUtil.getExpiredTime(newToken))
                                    .build().toEntity())
                    , jwtUtil.createConnectToken()
            );
        }
    }

    public UserListResponseDto findCurrentUserList() {
        Set<SimpUser> usersSet = simpUserRegistry.getUsers();
        List<String> tokens = new ArrayList<>();

        for (SimpUser user : usersSet) {
            tokens.add(user.getName());
        }

        return UserListResponseDto.builder()
                .dataType(DataType.CONNECTED_USER_LIST.getDataType())
                .users(userRepository.findAllByUserTokenIn(tokens))
                .build();
    }

    private boolean isJoinedUser(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }
}
