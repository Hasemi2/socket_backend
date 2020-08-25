package com.example.socket.service;

import com.example.dto.ChatRoomSaveRequestDto;
import com.example.socket.domain.ChatRoom;
import com.example.socket.repository.ChatRoomRepository;
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
