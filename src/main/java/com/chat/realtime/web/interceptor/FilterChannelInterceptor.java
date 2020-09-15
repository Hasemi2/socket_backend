package com.chat.realtime.web.interceptor;

import com.chat.realtime.web.exception.CommonException;
import com.chat.realtime.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
public class FilterChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        log.info("full message:" + message);
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            log.info("CONNECT ==================================");
            String token = headerAccessor.getFirstNativeHeader("Authorization");
            log.info("Authorization Token : " + token);

            // TODO: 2020-09-09 개발 임시 삭제 예정
            if ("SUPER_TOKEN".equals(token)) {
                return message;
            }

            if (!jwtUtil.isValidToken(token)) {
                throw new CommonException(401, "유효한 토큰이 아닙니다.");
            }

        } else if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            log.info("SUBSCRIBE ==================================");
        } else if (StompCommand.SEND.equals(headerAccessor.getCommand())) {
            log.info("SEND ==================================");
        } else if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            log.info("DISCONNECT ==================================");
        }


        return message;
    }
}