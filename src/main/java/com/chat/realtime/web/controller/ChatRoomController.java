package com.chat.realtime.web.controller;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.service.ChatRoomService;
import com.chat.realtime.web.dto.ChatRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     * @param chatRoomSaveRequestDto
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Long create(@RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        return chatRoomService.save(chatRoomSaveRequestDto);
    }

    /**
     * 유저가 참여해 있는 채팅방 리스트 요청
     * @return
     * @throws Exception
     */
    @MessageMapping("/room/list/get")
    @SendTo("/topic/room")
    public List<ChatRoom> roomLists() throws Exception {
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        return chatRooms;

    }
}
