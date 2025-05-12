package com.tictactoe.game.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseStatusException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ResponseStatusException(HttpStatus httpStatus, String code, String message) {
        super();
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
