package com.chat.realtime.web.controller;

import com.chat.realtime.service.ChatRoomService;
import com.chat.realtime.web.dto.*;
import com.chat.realtime.web.dto.type.DataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final SimpMessageSendingOperations messagingTemplate;

    private final SimpUserRegistry userRegistry;

    /**
     * 유저가 참여해 있는 채팅방 리스트 요청
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/roomList/get")
    @SendToUser("/topic/room")
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
    @SendToUser("/topic/room")
    public ChatRoomSaveResponseDto add(ChatRoomSaveRequestDto requestDto, SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        log.info("headerAcceseor" + headerAccessor.toString());
        log.info("token " + token);
        requestDto.setUserToken(token);
        log.info(requestDto.toString());
        ChatRoomSaveResponseDto responseDto = chatRoomService.addRoom(requestDto);
        String receiverToken = chatRoomService.findReceiverToken(requestDto.getReceiver());
        messagingTemplate.convertAndSendToUser(receiverToken, "/topic/room", responseDto); //receiver broadcast
        return responseDto;
    }

    /**
     * 유저 채팅방 입장
     *
     * @param roomId
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/enter/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public void enter(@DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        messagingTemplate.convertAndSend(format("/topic/room/%s", roomId), chatRoomService.findChatByRoomId(roomId));
        //messagingTemplate.convertAndSendToUser(token, format("/topic/room/%s", roomId), chatRoomService.findChatByRoomId(roomId));
    }

    /**
     * 유저 채팅방 나가기 요청`
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/leave")
    @SendToUser("/topic/room")
    public ChatRoomLeaveResponseDto leaveRoom(SimpMessageHeaderAccessor headerAccessor, ChatRoomLeaveRequestDto requestDto) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        requestDto.setUserToken(token);
        return chatRoomService.leaveRoom(requestDto);
    }
}
