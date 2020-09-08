package com.chat.realtime.domain.mapping;

import com.chat.realtime.domain.BaseTimeEntity;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "TB_CHAT_USER_MAPPING")
@Entity
public class UserChatRoom extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long chatUserId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @Builder
    public UserChatRoom(User user, ChatRoom room) {
        this.user = user;
        this.room = room;
    }
}
