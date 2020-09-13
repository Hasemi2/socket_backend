package com.chat.realtime.service;

import com.chat.realtime.domain.mapping.UserChatRoom;
import com.chat.realtime.domain.mapping.UserChatRoomRepository;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.room.ChatRoomRepository;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.*;
import com.chat.realtime.web.dto.type.DataType;
import com.chat.realtime.web.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final UserChatRoomRepository userChatRoomRepository;

    @Transactional
    public ChatRoomListResponseDto findRoomLists(String userToken) {
        String myId = userRepository.findByUserToken(userToken).get().getUserId();
        return ChatRoomListResponseDto.builder()
                .dataType(DataType.GET_ROOM_LIST.getDataType())
                .rooms(chatRoomRepository.findByUserId(myId))
                .build();
    }

    @Transactional
    public ChatRoomSaveResponseDto addRoom(ChatRoomSaveRequestDto requestDto) {

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().build());

        String myId = userRepository.findByUserToken(requestDto.getUserToken()).get().getUserId();
        String receiverId = requestDto.getReceiver();

        userChatRoomRepository.save(
                UserChatRoom.builder()
                        .room(chatRoom)
                        .user(userRepository.findByUserId(myId).orElseThrow(() -> new CommonException(400, "사용자 정보가 없습니다.")))
                        .build()
        );

        userChatRoomRepository.save(
                UserChatRoom.builder()
                        .room(chatRoom)
                        .user(userRepository.findByUserId(receiverId).orElseThrow(() -> new CommonException(400, "사용자 정보가 없습니다.")))
                        .build()
        );


        return ChatRoomSaveResponseDto.builder()
                .dataType(DataType.ADD_ROOM.getDataType())
                .data(ChatRoomSaveResponseDto
                        .Data.builder()
                        .myId(myId)
                        .receiver(receiverId)
                        .roomId(chatRoom.getRoomId())
                        .build())
                .build();
    }

    @Transactional
    public ChatRoomLeaveResponseDto leaveRoom(ChatRoomLeaveRequestDto requestDto) {
        //매핑 테이블 삭제

        //매핑테이블에 조회 안될시 roomtable delete

        //채팅방 정보-> 채팅방 아이디, 채팅방 참여인원등의 정보 || 참여인원이 0명일시 null 리턴
        
        return null;
    }
}
