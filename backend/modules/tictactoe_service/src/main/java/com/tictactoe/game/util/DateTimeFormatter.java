package com.tictactoe.game.util;

import java.time.LocalDateTime;

public class DateTimeFormatter {

    public static String toString(LocalDateTime dateTime) {
        String formattedDate = "";
        if (dateTime != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedDate = dateTime.format(formatter);
        }
        return formattedDate;
    }
}
