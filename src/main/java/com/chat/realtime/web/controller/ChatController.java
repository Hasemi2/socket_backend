package com.chat.realtime.web.controller;

import com.chat.realtime.service.ChatService;
import com.chat.realtime.web.dto.ChatSaveRequestDto;
import com.chat.realtime.web.dto.ChatSaveResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}/message/send")
    @SendTo("/topic/room/{roomId}") //해당방 유저들에게 브로드캐스트
    public ChatSaveResponseDto send(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String roomId, ChatSaveRequestDto chatSaveRequestDto) {
        log.info("send=============================");
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        chatSaveRequestDto.setRoomId(Long.parseLong(roomId));
        chatSaveRequestDto.setUserToken(token);
        return chatService.saveChatMessage(chatSaveRequestDto);
    }
}
