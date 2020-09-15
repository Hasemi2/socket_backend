package com.chat.realtime.web.controller;

import com.chat.realtime.service.ChatRoomService;
import com.chat.realtime.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    /**
     * 유저가 참여해 있는 채팅방 리스트 요청
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/roomList/get")
    @SendTo("/topic/room")
    public ChatRoomListResponseDto roomLists(SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        return chatRoomService.findRoomLists(token);

    }

    /**
     * 유저 채팅방 생성(채팅 신청) 요청
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/add")
    @SendTo("/topic/room")
    public ChatRoomSaveResponseDto add(SimpMessageHeaderAccessor headerAccessor, ChatRoomSaveRequestDto requestDto) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        requestDto.setUserToken(token);
        return chatRoomService.addRoom(requestDto);
    }

    /**
     * 유저 채팅방 나가기 요청`
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/leave")
    @SendTo("/topic/room")
    public ChatRoomLeaveResponseDto leaveRoom(SimpMessageHeaderAccessor headerAccessor, ChatRoomLeaveRequestDto requestDto) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        requestDto.setUserToken(token);
        return chatRoomService.leaveRoom(requestDto);
    }
}
