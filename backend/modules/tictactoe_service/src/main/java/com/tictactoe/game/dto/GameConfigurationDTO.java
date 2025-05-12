package com.tictactoe.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameConfigurationDTO {

    private List<GameMode> gameMode;
    private List<BoardSizeConfiguration> boardSizeConfiguration;
    private List<AIDifficulty> aiDifficulties;
    private List<TurnTimeDurationConfiguration> turnTimeDurationConfiguration;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GameMode {
        private UUID id;
        private String mode;
        private String label;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BoardSizeConfiguration {
        private UUID id;
        private int value;
        private String label;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AIDifficulty {
        private UUID id;
        private String value;
        private String label;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TurnTimeDurationConfiguration {
        private UUID id;
        private int value;
        private String label;
    }
}
