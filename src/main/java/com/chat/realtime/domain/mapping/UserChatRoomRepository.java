package com.chat.realtime.domain.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {

    @Modifying
    @Query(value = "DELETE FROM TB_CHAT_USER_MAPPING MA WHERE MA.USER_ID =:userId AND MA.ROOM_ID = :roomId", nativeQuery = true)
    void deleteByUserIdAndRoomId(@Param("userId") String userId, @Param("roomId") Long roomId);
}
