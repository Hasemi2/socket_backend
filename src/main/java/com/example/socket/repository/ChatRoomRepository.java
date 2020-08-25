package com.example.socket.repository;

import com.example.socket.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , Long> {
}
