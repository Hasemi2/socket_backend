package com.chat.realtime.domain.user;

import com.chat.realtime.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sun.nio.cs.US_ASCII;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

// TODO: 2020-08-30 json 필드 추가하기
@Getter
@NoArgsConstructor
@Table(name = "TB_CHAT_USER")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userPk;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String useYn = "Y";

    @Column
    private String userToken;

    @Column
    private LocalDateTime tokenExpiredTime;

    @Builder
    public User(String userId, String password, String userToken, LocalDateTime tokenExpiredTime) {
        this.userId = userId;
        this.password = password;
        this.userToken = userToken;
        this.tokenExpiredTime = tokenExpiredTime;
    }

    public void update(String userToken, LocalDateTime tokenExpiredTime) {
        this.userToken = userToken;
        this.tokenExpiredTime = tokenExpiredTime;
    }
}
