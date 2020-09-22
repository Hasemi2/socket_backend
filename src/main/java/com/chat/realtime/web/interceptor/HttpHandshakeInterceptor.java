package com.chat.realtime.web.interceptor;

import com.chat.realtime.web.connect.TokenMapper;
import com.chat.realtime.web.exception.CommonException;
import com.chat.realtime.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 쿼리스트링으로 넘어온 connect 토큰과 유저 인증 토큰 매칭 후 이 단계에서 검증
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("beforeHandshake ====================================== ");
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
        log.info("seesion -> " + httpServletRequest.getSession().getId());

        String connectToken = httpServletRequest.getParameter("connect_token");
        log.info("connect_token  : " + connectToken);
        String authToken = TokenMapper.get(connectToken);
        log.info("authToken  : " + authToken);

        // TODO: 2020-09-09 개발 임시 삭제 예정
        if ("SUPER_TOKEN".equals(authToken)) {
            return true;
        }
        if (!jwtUtil.isValidToken(authToken)) {
            throw new CommonException(401, "유효한 토큰이 아닙니다.");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("afterHandshake ========================================");

    }


}