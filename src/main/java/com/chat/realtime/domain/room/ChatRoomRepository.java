package com.chat.realtime.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , Long> {

    @Query(value = "SELECT TCR.* FROM TB_CHAT_ROOM TCR JOIN TB_CHAT_USER_MAPPING MA ON MA.USER_ID =:userId", nativeQuery = true)
    List<ChatRoom> findAllByUserId(@Param("userId") String myId);
}
