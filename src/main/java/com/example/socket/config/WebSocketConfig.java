package com.example.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configure Spring for STOMP messaging
 * Stomp : Simple Text Oriented Messaging Protocol 단순 텍스트 지향 메세지 프로토콜
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); //메세지 브로커 등록 subscribe개념
        config.setApplicationDestinationPrefixes("/app"); //클라이언트에서 메세지 송신시 붙여줄 prefix publish개념
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/test").setAllowedOrigins("*").withSockJS(); //클라이언트가 접속할 웹 소켓 주소, CORS 허용
    }

}
