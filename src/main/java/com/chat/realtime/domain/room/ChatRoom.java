package com.chat.realtime.domain.room;


import com.chat.realtime.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// TODO: 2020-08-30 json 필드 추가하기
@Getter
@NoArgsConstructor
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long roomId;

    @Column(length = 50, nullable = false)
    private String roomName;

    @Column(nullable = false)
    private char useYn;

    @Builder
    public ChatRoom(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.useYn = 'Y';
    }
}
