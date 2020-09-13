package com.chat.realtime.web.controller;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.service.ChatRoomService;
import com.chat.realtime.web.dto.*;
import com.chat.realtime.web.dto.type.DataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

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
    public ChatRoomListResponseDto roomLists(@Header("nativeHeaders") LinkedMultiValueMap<String, String> authorization) {
        List<String> list = (List<String>) authorization.get("Authorization");
        String token = list.get(0);
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
    public ChatRoomSaveResponseDto add(@Header("nativeHeaders") LinkedMultiValueMap<String, String> authorization, ChatRoomSaveRequestDto requestDto) {
        List<String> list = (List<String>) authorization.get("Authorization");
        String token = list.get(0);
        requestDto.setUserToken(token);
        // TODO: 2020-09-09 emit 요청 구조잡기 협의협의
        return chatRoomService.addRoom(requestDto);
    }

    /**
     * 유저 채팅방 나가기 요청
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/leave")
    @SendTo("/topic/room")
    public ChatRoomLeaveResponseDto leaveRoom(@Header("nativeHeaders") LinkedMultiValueMap<String, String> authorization, ChatRoomLeaveRequestDto requestDto) {
        List<String> list = (List<String>) authorization.get("Authorization");
        String token = list.get(0);
        requestDto.setUserToken(token);
        return chatRoomService.leaveRoom(requestDto);
    }
}
