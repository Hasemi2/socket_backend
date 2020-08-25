package com.example.socket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class TestChat {

	private String name;
	private String message;

	
    @Builder
    public TestChat(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
