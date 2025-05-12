package com.tictactoe.game.service;

import com.tictactoe.game.constant.ErrorCode;
import com.tictactoe.game.dto.GameConfigurationDTO;
import com.tictactoe.game.exception.ResponseStatusException;
import com.tictactoe.game.model.AIDifficulty;
import com.tictactoe.game.model.BoardSizeConfiguration;
import com.tictactoe.game.model.GameMode;
import com.tictactoe.game.model.TurnTimeDurationConfiguration;
import com.tictactoe.game.repository.AIDifficultyRepository;
import com.tictactoe.game.repository.BoardSizeConfigurationRepository;
import com.tictactoe.game.repository.GameModeRepository;
import com.tictactoe.game.repository.TurnTimeDurationConfigurationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

//@CustomLog
@Service
public class GameConfigurationServiceImpl implements GameConfigurationService {
    private final GameModeRepository gameModeRepository;
    private final BoardSizeConfigurationRepository boardSizeConfigurationRepository;
    private final AIDifficultyRepository aiDifficultyRepository;
    private final TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository;

    public GameConfigurationServiceImpl(GameModeRepository gameModeRepository, BoardSizeConfigurationRepository boardSizeConfigurationRepository,
                                        AIDifficultyRepository aiDifficultyRepository, TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository) {
        this.gameModeRepository = gameModeRepository;
        this.boardSizeConfigurationRepository = boardSizeConfigurationRepository;
        this.aiDifficultyRepository = aiDifficultyRepository;
        this.turnTimeDurationConfigurationRepository = turnTimeDurationConfigurationRepository;
    }

    public GameConfigurationDTO getConfiguration() {
        try {
            GameConfigurationDTO configurationDTO = new GameConfigurationDTO();
            List<GameMode> gameModeList = gameModeRepository.findAll();
            configurationDTO.setGameMode(gameModeList.stream().map(gameMode -> GameConfigurationDTO.GameMode.builder()
                    .id(gameMode.getId())
                    .mode(gameMode.getMode())
                    .label(gameMode.getLabel())
                    .build()).toList());

            List<BoardSizeConfiguration> boardSizeConfigurationList = boardSizeConfigurationRepository.findAll();
            configurationDTO.setBoardSizeConfiguration(boardSizeConfigurationList.stream().map(boardSizeConfiguration -> GameConfigurationDTO.BoardSizeConfiguration.builder()
                    .id(boardSizeConfiguration.getId())
                    .value(boardSizeConfiguration.getValue())
                    .label(boardSizeConfiguration.getLabel())
                    .build()).toList());

            List<AIDifficulty> aiDifficultyList = aiDifficultyRepository.findAll();
            configurationDTO.setAiDifficulties(aiDifficultyList.stream().map(aiDifficulty -> GameConfigurationDTO.AIDifficulty.builder()
                    .id(aiDifficulty.getId())
                    .value(aiDifficulty.getValue())
                    .label(aiDifficulty.getLabel())
                    .build()).toList());

            List<TurnTimeDurationConfiguration> turnTimeDurationConfigurations = turnTimeDurationConfigurationRepository.findAll();
            configurationDTO.setTurnTimeDurationConfiguration(turnTimeDurationConfigurations.stream().map(turnTimeDurationConfiguration -> GameConfigurationDTO.TurnTimeDurationConfiguration.builder()
                    .id(turnTimeDurationConfiguration.getId())
                    .value(turnTimeDurationConfiguration.getValue())
                    .label(turnTimeDurationConfiguration.getLabel())
                    .build()).toList());

            return configurationDTO;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    ErrorCode.SERVER_ERROR.getCode(),
                    ErrorCode.SERVER_ERROR.getMessage());
        }
    }

}