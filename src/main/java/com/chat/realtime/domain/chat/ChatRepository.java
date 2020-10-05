package com.chat.realtime.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository  extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM TB_CHAT_CONTENT WHERE ROOM_ID = :roomId" , nativeQuery = true)
    List<Chat> findAllByRoomId(@Param("roomId")Long roomId);
}
