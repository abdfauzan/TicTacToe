package com.tictactoe.game.service;

import com.tictactoe.game.dto.BoardSizeConfigurationDTO;
import com.tictactoe.game.model.BoardSizeConfiguration;
import com.tictactoe.game.repository.BoardSizeConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardSizeConfigurationServiceTest {

    @Mock
    private BoardSizeConfigurationRepository boardSizeConfigurationRepository;

    @InjectMocks
    private BoardSizeConfigurationService service;

    @Test
    void getList_unitTest() {
        BoardSizeConfiguration boardSizeConfiguration = BoardSizeConfiguration.builder()
                .value(113)
                .build();

        Pageable pageable = PageRequest.of(0, 13);
        List<BoardSizeConfiguration> content = Arrays.asList(boardSizeConfiguration);
        Page<BoardSizeConfiguration> page = new PageImpl<>(content, pageable, content.size());

        when(boardSizeConfigurationRepository.findAll(pageable)).thenReturn(page);

        var result = service.getList(1, 13);

        assertEquals(113, result.getData().get(0).getValue());
        assertEquals(2, result.getData().size() + 1);
    }

    @Test
    void save_unitTest() {
        BoardSizeConfiguration boardSizeConfiguration = BoardSizeConfiguration.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 x 113")
                .build();

        BoardSizeConfigurationDTO boardSizeConfigurationDTO = BoardSizeConfigurationDTO.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 x 113")
                .build();

        when(boardSizeConfigurationRepository.save(boardSizeConfiguration)).thenReturn(boardSizeConfiguration);
        when(boardSizeConfigurationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(Optional.of(boardSizeConfiguration));
        var result = service.save(boardSizeConfigurationDTO);

        assertEquals("OK", result.getCode());
        assertEquals("Success updated board size configuration data", result.getMessage());
    }

    @Test
    void getDetail_unitTest() {
        BoardSizeConfiguration boardSizeConfiguration = BoardSizeConfiguration.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 x 113")
                .build();

        when(boardSizeConfigurationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(Optional.of(boardSizeConfiguration));

        var result = service.getDetail(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        assertEquals(113, result.getValue());
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result.getId());
    }
}