package com.example.socket.controller;

import com.example.socket.domain.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SocketController {


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat") //해당 topic을 수신하는 클라이언트 웹소켓에 메세지 전달
    public Chat greeting(Chat chat) throws Exception {
         return Chat.builder().name(chat.getName()).message(chat.getMessage()).build();
    }

}
