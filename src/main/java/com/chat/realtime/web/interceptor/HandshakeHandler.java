package com.chat.realtime.web.interceptor;

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
        String token = httpServletRequest.getParameter("Authorization");
        log.info("sessionId : " + sessionId);
        log.info("parameter? " + httpServletRequest.getParameter("Authorization"));
        log.info("QueryString? " + httpServletRequest.getQueryString());
        return new RoleUser(token);
    }

}