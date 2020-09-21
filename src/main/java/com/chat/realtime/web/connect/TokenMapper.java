package com.chat.realtime.web.connect;

import com.chat.realtime.web.exception.CommonException;
import com.chat.realtime.web.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 커넥트용 토큰 ===== 유저 인증용 토큰 매핑 클래스
 */
public class TokenMapper {

    @Autowired
    private static JwtUtil jwtUtil;

    private static Map<String, String> tokenMap;

    private TokenMapper() {
    }

    public static Map<String, String> getInstance() {
        if (tokenMap == null) {
            return tokenMap = new HashMap<>();
        }
        return tokenMap;
    }

    public static String get(String connectToken) {
        // TODO: 2020-09-21 개발임시
        if (connectToken.equals("CONNECT_TOKEN")) {
            return TokenMapper.getInstance().get(connectToken);
        }

        if (!jwtUtil.isValidToken(connectToken)) {
            throw new CommonException(401, "커넥트용 인증 토큰 만료시간이 지났습니다.");
        }
        return TokenMapper.getInstance().get(connectToken);
    }

    public static void set(String connectToken, String authToken) {
        if (TokenMapper.getInstance().containsKey(connectToken)) {
            throw new CommonException(401, "인증 실패");
        }

        TokenMapper.getInstance().put(connectToken, authToken);
    }

    public static void remove(String connectToken) {
        TokenMapper.getInstance().remove(connectToken);
    }

}
