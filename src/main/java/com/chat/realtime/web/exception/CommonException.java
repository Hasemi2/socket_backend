package com.chat.realtime.web.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {

    private int status;

    private String message;

    public CommonException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
