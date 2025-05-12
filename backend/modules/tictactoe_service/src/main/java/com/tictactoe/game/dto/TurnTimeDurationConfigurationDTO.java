package com.tictactoe.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnTimeDurationConfigurationDTO {

    private UUID id;
    private int value;
    private String label;
    private String createdAt;
    private String updatedAt;
}
