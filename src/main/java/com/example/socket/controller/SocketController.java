package com.example.socket.controller;

import com.example.dto.ChatRoomSaveRequestDto;
import com.example.socket.domain.Chat;
import com.example.socket.domain.ChatRoom;
import com.example.socket.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SocketController {

    private final ChatRoomService chatRoomService;

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @GetMapping("/")
    public String home(Model model) {
        //List<ChatRoom> chatRooms = chatRoomService.findAll();
        //model.addAttribute("chatRooms", chatRooms);
        return "list";
    }

    @PostMapping("/create")
    @ResponseBody
    public Long create(@RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        return chatRoomService.save(chatRoomSaveRequestDto);
    }

    //채팅방 데이터 구조 name, id, createdDate, 채팅내역
    //app/roomLists
    @MessageMapping("/roomLists")
    @SendTo("/topic/roomLists")
    public List<ChatRoom> roomLists(@Payload Chat param) throws Exception {

        log.info("param.getName() ===> " , param.getName());

        List<ChatRoom> rooms = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ChatRoom chatRoom = ChatRoom.builder().roomName("test " + i).roomId((long) i).build();
            rooms.add(chatRoom);
        }

        return rooms;

    }

}
