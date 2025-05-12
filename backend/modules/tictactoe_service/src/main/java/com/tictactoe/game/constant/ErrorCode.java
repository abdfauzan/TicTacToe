package com.tictactoe.game.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SERVER_ERROR("ERR.200.02.200.01", "Application dealing with Unexpected Error. Please inform your application administrator"),
    USERNAME_IS_EXIST_ERROR("ERR.200.02.200.02", "Username is already taken"),
    BOARD_SIZE_CONFIGURATION_NOT_FOUND_ERROR("ERR.200.02.200.03", "Data Board Size Configuration Not Found"),
    TURN_TIME_DURATION_CONFIGURATION_NOT_FOUND_ERROR("ERR.200.02.200.04", "Data Turn Time Duration Configuration Not Found");

    private final String code;
    private final String message;
}
