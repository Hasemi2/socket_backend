package com.chat.realtime.web.connect;

import com.chat.realtime.web.exception.CommonException;
import com.chat.realtime.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 커넥트용 토큰 ===== 유저 인증용 토큰 매핑 클래스
 */
@Slf4j
@Component
public class TokenMapper {
    // FIXME   클래스 로드 시점이 스프링에서 관리하는 @Autowired 시점과 맞지 않음. 클래스 로더에 의해
    // FIXME: 2020-09-22  해당 클래스라 인스턴스화 될 때 스프링 컨텍스는 아직 로드되지 않아 주입이 되지 않는 문제
    private static JwtUtil staticJwtUtil;

    @Autowired
    private JwtUtil jwtUtil;

    private static Map<String, String> tokenMap;

    private TokenMapper() {
    }

    @PostConstruct
    private void initialize(){
        this.staticJwtUtil = this.jwtUtil;
    }

    public static Map<String, String> getInstance() {
        if (tokenMap == null) {
            return tokenMap = new HashMap<>();
        }
        return tokenMap;
    }

    public static String get(String connectToken) {
        if (!staticJwtUtil.isValidToken(connectToken)) {
            throw new CommonException(401, "커넥트용 인증 토큰 만료시간이 지났습니다.");
        }
        return getInstance().get(connectToken);
    }

    public static void set(String connectToken, String authToken) {
        if (getInstance().containsKey(connectToken)) {
            throw new CommonException(401, "인증 실패");
        }

        getInstance().put(connectToken, authToken);
    }

    public static void remove(String connectToken) {
        getInstance().remove(connectToken);
    }

}
