package com.tictactoe.game.service;

import com.tictactoe.game.constant.ErrorCode;
import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationsDTO;
import com.tictactoe.game.exception.ResponseStatusException;
import com.tictactoe.game.model.TurnTimeDurationConfiguration;
import com.tictactoe.game.repository.TurnTimeDurationConfigurationRepository;
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
public class TurnTimeDurationConfigurationService {

    private final TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository;

    public TurnTimeDurationConfigurationService(TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository) {
        this.turnTimeDurationConfigurationRepository = turnTimeDurationConfigurationRepository;
    }

    public TurnTimeDurationConfigurationsDTO getList(Integer page, Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<TurnTimeDurationConfiguration> list = turnTimeDurationConfigurationRepository.findAll(pageable);

            List<TurnTimeDurationConfigurationDTO> turnTimeDurationConfigurationDTOList = list.stream().map(turnTimeDurationConfiguration -> TurnTimeDurationConfigurationDTO.builder()
                    .id(turnTimeDurationConfiguration.getId())
                    .value(turnTimeDurationConfiguration.getValue())
                    .label(turnTimeDurationConfiguration.getLabel())
                    .createdAt(DateTimeFormatter.toString(turnTimeDurationConfiguration.getCreatedAt()))
                    .updatedAt(DateTimeFormatter.toString(turnTimeDurationConfiguration.getUpdatedAt()))
                    .build()
            ).toList();

            TurnTimeDurationConfigurationsDTO.Paging paging = new TurnTimeDurationConfigurationsDTO.Paging();
            paging.setSize(list.getSize());
            paging.setPage(list.getTotalPages());
            paging.setTotalRecords(list.getTotalElements());
            TurnTimeDurationConfigurationsDTO turnTimeDurationConfigurationsDTO = new TurnTimeDurationConfigurationsDTO(turnTimeDurationConfigurationDTOList, paging);

            return turnTimeDurationConfigurationsDTO;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    ErrorCode.SERVER_ERROR.getCode(),
                    ErrorCode.SERVER_ERROR.getMessage());
        }
    }

    public StatusDetailDTO save(TurnTimeDurationConfigurationDTO turnTimeDurationConfigurationDTO) {
        try {
            StatusDetailDTO statusDetailDTO = new StatusDetailDTO();
            if (turnTimeDurationConfigurationDTO.getId() == null) {
                TurnTimeDurationConfiguration turnTimeDurationConfiguration = new TurnTimeDurationConfiguration();
                turnTimeDurationConfiguration.setValue(turnTimeDurationConfigurationDTO.getValue());
                turnTimeDurationConfiguration.setLabel(turnTimeDurationConfigurationDTO.getLabel());
                turnTimeDurationConfiguration.setCreatedBy("application");

                turnTimeDurationConfigurationRepository.save(turnTimeDurationConfiguration);
                statusDetailDTO.setCode(HttpStatus.OK.name());
                statusDetailDTO.setMessage("Success saved turn time configuration data");
            } else {

                TurnTimeDurationConfiguration turnTimeDurationConfiguration = turnTimeDurationConfigurationRepository.findById(turnTimeDurationConfigurationDTO.getId()).orElse(
                        null
                );
                if (turnTimeDurationConfiguration == null) {
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                            ErrorCode.TURN_TIME_DURATION_CONFIGURATION_NOT_FOUND_ERROR.getCode(),
                            ErrorCode.TURN_TIME_DURATION_CONFIGURATION_NOT_FOUND_ERROR.getMessage());
                }

                if (turnTimeDurationConfigurationDTO.getValue() != 0) {
                    turnTimeDurationConfiguration.setValue(turnTimeDurationConfigurationDTO.getValue());
                }
                if (StringUtils.isNotEmpty(turnTimeDurationConfigurationDTO.getLabel())) {
                    turnTimeDurationConfiguration.setLabel(turnTimeDurationConfigurationDTO.getLabel());
                }

                turnTimeDurationConfigurationRepository.save(turnTimeDurationConfiguration);
                statusDetailDTO.setCode(HttpStatus.OK.name());
                statusDetailDTO.setMessage("Success updated turn time configuration data");
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

    public TurnTimeDurationConfigurationDTO getDetail(UUID uuid) {
        try {
            TurnTimeDurationConfiguration turnTimeDurationConfiguration = turnTimeDurationConfigurationRepository.findById(uuid).orElse(
                    null
            );
            if (turnTimeDurationConfiguration == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                        ErrorCode.TURN_TIME_DURATION_CONFIGURATION_NOT_FOUND_ERROR.getCode(),
                        ErrorCode.TURN_TIME_DURATION_CONFIGURATION_NOT_FOUND_ERROR.getMessage());
            }

            return TurnTimeDurationConfigurationDTO.builder()
                    .id(turnTimeDurationConfiguration.getId())
                    .value(turnTimeDurationConfiguration.getValue())
                    .label(turnTimeDurationConfiguration.getLabel())
                    .createdAt(DateTimeFormatter.toString(turnTimeDurationConfiguration.getCreatedAt()))
                    .updatedAt(DateTimeFormatter.toString(turnTimeDurationConfiguration.getUpdatedAt()))
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