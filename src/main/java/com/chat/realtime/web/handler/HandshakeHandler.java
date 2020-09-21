package com.chat.realtime.web.handler;

import com.chat.realtime.web.connect.TokenMapper;
import com.chat.realtime.web.controller.RoleUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Slf4j
public class HandshakeHandler extends DefaultHandshakeHandler {

    /**
     * Client Socket Connect 시 호출 순서-----------------------------------
     * HttpHandShakeInterceptor beforeHandShake
     * HandshakeHandler determineUser
     * HttpHandShakeInterceptor AfterHandShake
     * FilterChannelInterceptor
     *
     * @param request
     * @param wsHandler
     * @param attributes
     * @return
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("determineUser =============================== ");
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

        String sessionId = httpServletRequest.getSession().getId();
        String connectToken = httpServletRequest.getParameter("connect_token");
        log.info("sessionId : " + sessionId);
        log.info("connectToken : " + connectToken);
        String authToken = TokenMapper.get(connectToken);

        log.info("authToken : " + authToken);
        log.info("before : " + TokenMapper.getInstance().toString());

        RoleUser user = new RoleUser(authToken); // 유저 권한 생성후
        TokenMapper.remove(connectToken); //해당 임시 토큰 데이터 삭제

        log.info("after : " + TokenMapper.getInstance().toString());
        return new RoleUser(authToken);

    }
}