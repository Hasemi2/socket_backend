package com.example.socket.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long roomId;

    @Column(length = 500, nullable = false)
    private String roomName;

    @Builder
    public ChatRoom(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
