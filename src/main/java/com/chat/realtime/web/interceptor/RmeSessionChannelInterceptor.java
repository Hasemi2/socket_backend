package com.chat.realtime.web.interceptor;

import com.chat.realtime.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

@Slf4j
public class RmeSessionChannelInterceptor implements ChannelInterceptor {


    @Autowired
    private  JwtUtil jwtUtil;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Channel Interceptor");

        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

        log.info("multiValueMap.toString() " + multiValueMap.toString());
        log.info("Authorization => " + multiValueMap.get("Authorization"));

        Set<String> keys = multiValueMap.keySet();

        for (String key : keys) {
            System.out.println("Key = " + key);
            System.out.println("Values = " + multiValueMap.get(key));
            List<String> list = (List<String>) multiValueMap.get(key);
            String token = list.get(0);
            log.info("jwtUtil.isValidToken()  " + jwtUtil.isValidToken(token));
        }




        return message;
    }
}