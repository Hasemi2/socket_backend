package com.chat.realtime.domain.room;


import com.chat.realtime.domain.BaseTimeEntity;
import com.chat.realtime.domain.mapping.UserChatRoom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

// TODO: 2020-08-30 json 필드 추가하기
@Getter
@NoArgsConstructor
@Table(name = "TB_CHAT_ROOM")
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long roomId;

    @Column(nullable = false)
    private String useYn = "Y";

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    Set<UserChatRoom> userChatRooms;

    @Builder
    public ChatRoom(Long roomId) {
        this.roomId = roomId;
    }
}
