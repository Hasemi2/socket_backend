package com.chat.realtime.config;

import com.chat.realtime.web.interceptor.FilterChannelInterceptor;
import com.chat.realtime.web.interceptor.HandshakeHandler;
import com.chat.realtime.web.interceptor.HttpHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configure Spring for STOMP messaging
 * Stomp : Simple Text Oriented Messaging Protocol 단순 텍스트 지향 메세지 프로토콜
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 합니다.
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/room", "/user", "/chat"); //클라이언트에서 메세지 송신시 붙여줄 prefix publish개념
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/test")
                .addInterceptors(new HttpHandshakeInterceptor())
                .setHandshakeHandler(new HandshakeHandler())
                .setAllowedOrigins("*").withSockJS(); //클라이언트가 접속할 웹 소켓 주소, CORS 허용
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(filterChannelInterceptor());
    }

    @Bean
    public FilterChannelInterceptor filterChannelInterceptor() {
        return new FilterChannelInterceptor();
    }


}
