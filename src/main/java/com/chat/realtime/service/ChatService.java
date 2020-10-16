package com.chat.realtime.service;

import com.chat.realtime.domain.chat.Chat;
import com.chat.realtime.domain.chat.ChatRepository;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.room.ChatRoomRepository;
import com.chat.realtime.domain.user.User;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.ChatSaveRequestDto;
import com.chat.realtime.web.dto.ChatSaveResponseDto;
import com.chat.realtime.web.dto.type.DataType;
import com.chat.realtime.web.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatSaveResponseDto saveChatMessage(ChatSaveRequestDto chatSaveRequestDto) {

        User user = userRepository.findByUserToken(chatSaveRequestDto.getUserToken())
                .orElseThrow(() -> new CommonException(404, "사용자 정보가 없습니다."));

        ChatRoom room = chatRoomRepository.findById(chatSaveRequestDto.getRoomId())
                .orElseThrow(() -> new CommonException(404, "방 정보가 없습니다."));

        Chat chat = chatRepository.save(chatSaveRequestDto.toEntity(user, room, chatSaveRequestDto.getMessage()));
        return ChatSaveResponseDto.builder().dataType(DataType.CHAT_MESSAGE.getDataType())
                .chat(chat).build();
    }
}
