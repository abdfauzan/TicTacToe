package com.tictactoe.game.service;

import com.tictactoe.game.constant.ErrorCode;
import com.tictactoe.game.dto.BoardSizeConfigurationDTO;
import com.tictactoe.game.dto.BoardSizeConfigurationsDTO;
import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.exception.ResponseStatusException;
import com.tictactoe.game.model.BoardSizeConfiguration;
import com.tictactoe.game.repository.BoardSizeConfigurationRepository;
import com.tictactoe.game.util.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BoardSizeConfigurationService {

    private final BoardSizeConfigurationRepository boardSizeConfigurationRepository;

    public BoardSizeConfigurationService(BoardSizeConfigurationRepository boardSizeConfigurationRepository) {
        this.boardSizeConfigurationRepository = boardSizeConfigurationRepository;
    }

    public BoardSizeConfigurationsDTO getList(Integer page, Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<BoardSizeConfiguration> list = boardSizeConfigurationRepository.findAll(pageable);

            List<BoardSizeConfigurationDTO> boardSizeConfigurationDTOList = list.stream().map(boardSizeConfiguration -> BoardSizeConfigurationDTO.builder()
                    .id(boardSizeConfiguration.getId())
                    .value(boardSizeConfiguration.getValue())
                    .label(boardSizeConfiguration.getLabel())
                    .createdAt(DateTimeFormatter.toString(boardSizeConfiguration.getCreatedAt()))
                    .updatedAt(DateTimeFormatter.toString(boardSizeConfiguration.getUpdatedAt()))
                    .build()
            ).toList();

            BoardSizeConfigurationsDTO.Paging paging = new BoardSizeConfigurationsDTO.Paging();
            paging.setSize(list.getSize());
            paging.setPage(list.getTotalPages());
            paging.setTotalRecords(list.getTotalElements());
            BoardSizeConfigurationsDTO boardSizeConfigurationsDTO = new BoardSizeConfigurationsDTO(boardSizeConfigurationDTOList, paging);

            return boardSizeConfigurationsDTO;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    ErrorCode.SERVER_ERROR.getCode(),
                    ErrorCode.SERVER_ERROR.getMessage());
        }
    }

    public StatusDetailDTO save(BoardSizeConfigurationDTO boardSizeConfigurationDTO) {
        try {
            StatusDetailDTO statusDetailDTO = new StatusDetailDTO();
            if (boardSizeConfigurationDTO.getId() == null) {
                BoardSizeConfiguration boardSizeConfiguration = new BoardSizeConfiguration();
                boardSizeConfiguration.setValue(boardSizeConfigurationDTO.getValue());
                boardSizeConfiguration.setLabel(boardSizeConfigurationDTO.getLabel());
                boardSizeConfiguration.setCreatedBy("application");

                boardSizeConfigurationRepository.save(boardSizeConfiguration);
                statusDetailDTO.setCode(HttpStatus.OK.name());
                statusDetailDTO.setMessage("Success saved board size configuration data");
            } else {

                BoardSizeConfiguration boardSizeConfiguration = boardSizeConfigurationRepository.findById(boardSizeConfigurationDTO.getId()).orElse(
                        null
                );
                if (boardSizeConfiguration == null) {
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                            ErrorCode.BOARD_SIZE_CONFIGURATION_NOT_FOUND_ERROR.getCode(),
                            ErrorCode.BOARD_SIZE_CONFIGURATION_NOT_FOUND_ERROR.getMessage());
                }

                if (boardSizeConfigurationDTO.getValue() != 0) {
                    boardSizeConfiguration.setValue(boardSizeConfigurationDTO.getValue());
                }
                if (StringUtils.isNotEmpty(boardSizeConfigurationDTO.getLabel())) {
                    boardSizeConfiguration.setLabel(boardSizeConfigurationDTO.getLabel());
                }

                boardSizeConfigurationRepository.save(boardSizeConfiguration);
                statusDetailDTO.setCode(HttpStatus.OK.name());
                statusDetailDTO.setMessage("Success updated board size configuration data");
            }

            return statusDetailDTO;

        } catch (Exception exception) {
            if (exception instanceof ResponseStatusException) {
                throw exception;
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        ErrorCode.SERVER_ERROR.getCode(),
                        ErrorCode.SERVER_ERROR.getMessage());
            }
        }
    }

    public BoardSizeConfigurationDTO getDetail(UUID uuid) {
        try {
            BoardSizeConfiguration boardSizeConfiguration = boardSizeConfigurationRepository.findById(uuid).orElse(
                    null
            );
            if (boardSizeConfiguration == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                        ErrorCode.BOARD_SIZE_CONFIGURATION_NOT_FOUND_ERROR.getCode(),
                        ErrorCode.BOARD_SIZE_CONFIGURATION_NOT_FOUND_ERROR.getMessage());
            }

            return BoardSizeConfigurationDTO.builder()
                    .id(boardSizeConfiguration.getId())
                    .value(boardSizeConfiguration.getValue())
                    .label(boardSizeConfiguration.getLabel())
                    .createdAt(DateTimeFormatter.toString(boardSizeConfiguration.getCreatedAt()))
                    .updatedAt(DateTimeFormatter.toString(boardSizeConfiguration.getUpdatedAt()))
                    .build();

        } catch (Exception exception) {
            if (exception instanceof ResponseStatusException) {
                throw exception;
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        ErrorCode.SERVER_ERROR.getCode(),
                        ErrorCode.SERVER_ERROR.getMessage());
            }
        }
    }
}
