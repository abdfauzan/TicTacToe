package com.tictactoe.game.service;

import com.tictactoe.game.model.AIDifficulty;
import com.tictactoe.game.model.BoardSizeConfiguration;
import com.tictactoe.game.model.GameMode;
import com.tictactoe.game.model.TurnTimeDurationConfiguration;
import com.tictactoe.game.repository.AIDifficultyRepository;
import com.tictactoe.game.repository.BoardSizeConfigurationRepository;
import com.tictactoe.game.repository.GameModeRepository;
import com.tictactoe.game.repository.TurnTimeDurationConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameConfigurationServiceImplTest {

    @Mock
    private GameModeRepository gameModeRepository;

    @Mock
    private BoardSizeConfigurationRepository boardSizeConfigurationRepository;

    @Mock
    private AIDifficultyRepository aiDifficultyRepository;

    @Mock
    private TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository;

    @InjectMocks
    private GameConfigurationServiceImpl service;

    @Test
    void getConfiguration_unitTest() {
        TurnTimeDurationConfiguration turnTimeDurationConfiguration = TurnTimeDurationConfiguration.builder()
                .value(113)
                .build();
        List<TurnTimeDurationConfiguration> turnTimeDurationConfigurations = Arrays.asList(turnTimeDurationConfiguration);

        GameMode gameMode = GameMode.builder()
                .mode("Local")
                .build();
        List<GameMode> gameModes = Arrays.asList(gameMode);

        AIDifficulty aiDifficulty = AIDifficulty.builder()
                .value("expert")
                .build();
        List<AIDifficulty> aiDifficulties = Arrays.asList(aiDifficulty);

        BoardSizeConfiguration boardSizeConfiguration = BoardSizeConfiguration.builder()
                .value(113)
                .build();
        List<BoardSizeConfiguration> boardSizeConfigurationList = Arrays.asList(boardSizeConfiguration);

        when(gameModeRepository.findAll()).thenReturn(gameModes);
        when(aiDifficultyRepository.findAll()).thenReturn(aiDifficulties);
        when(boardSizeConfigurationRepository.findAll()).thenReturn(boardSizeConfigurationList);
        when(turnTimeDurationConfigurationRepository.findAll()).thenReturn(turnTimeDurationConfigurations);

        var result = service.getConfiguration();

        assertEquals(113, result.getTurnTimeDurationConfiguration().get(0).getValue());
        assertEquals("Local", result.getGameMode().get(0).getMode());
    }
}