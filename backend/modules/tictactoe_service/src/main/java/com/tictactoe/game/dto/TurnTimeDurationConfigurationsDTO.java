package com.tictactoe.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnTimeDurationConfigurationsDTO {

    List<TurnTimeDurationConfigurationDTO> data;
    Paging paging;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Paging {
        private int page;
        private int size;
        private long totalRecords;
    }
}
