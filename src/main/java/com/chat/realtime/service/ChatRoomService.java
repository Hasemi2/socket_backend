package com.chat.realtime.service;

import com.chat.realtime.domain.mapping.UserChatRoom;
import com.chat.realtime.domain.mapping.UserChatRoomRepository;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.room.ChatRoomRepository;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.ChatRoomListResponseDto;
import com.chat.realtime.web.dto.ChatRoomSaveRequestDto;
import com.chat.realtime.web.dto.ChatRoomSaveResponseDto;
import com.chat.realtime.web.dto.type.DataType;
import com.chat.realtime.web.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public ChatRoomSaveResponseDto save(ChatRoomSaveRequestDto requestDto) {

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
                .roomId(chatRoom.getRoomId())
                .dataType(DataType.ADD_ROOM.getDataType())
                .myId(myId)
                .receiver(receiverId)
                .build();
    }

}
