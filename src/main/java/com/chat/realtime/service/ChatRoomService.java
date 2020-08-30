package com.chat.realtime.service;

import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.web.dto.ChatRoomSaveRequestDto;
import com.chat.realtime.domain.room.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long save(ChatRoomSaveRequestDto requestDto) {
        return chatRoomRepository.save(requestDto.toEntity()).getRoomId();
    }

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }
}
