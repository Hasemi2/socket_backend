package com.chat.realtime.domain.chat;

import com.chat.realtime.domain.BaseTimeEntity;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "TB_CHAT_CONTENT")
@Entity
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatContentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String chatContent;

    @Builder
    public Chat(User user, ChatRoom room, String chatContent) {
        this.user = user;
        this.room = room;
        this.chatContent = chatContent;
    }
}
