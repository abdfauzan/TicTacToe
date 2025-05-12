package com.tictactoe.game.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTime {

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Singapore"));
    }
}