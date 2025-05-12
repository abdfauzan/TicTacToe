package com.tictactoe.game.service;

import com.tictactoe.game.dto.TurnTimeDurationConfigurationDTO;
import com.tictactoe.game.model.TurnTimeDurationConfiguration;
import com.tictactoe.game.repository.TurnTimeDurationConfigurationRepository;
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
class TurnTimeDurationConfigurationServiceTest {

    @Mock
    private TurnTimeDurationConfigurationRepository turnTimeDurationConfigurationRepository;

    @InjectMocks
    private TurnTimeDurationConfigurationService service;

    @Test
    void getList_unitTest() {
        TurnTimeDurationConfiguration turnTimeDurationConfiguration = TurnTimeDurationConfiguration.builder()
                .value(113)
                .build();

        Pageable pageable = PageRequest.of(0, 13);
        List<TurnTimeDurationConfiguration> content = Arrays.asList(turnTimeDurationConfiguration);
        Page<TurnTimeDurationConfiguration> page = new PageImpl<>(content, pageable, content.size());

        when(turnTimeDurationConfigurationRepository.findAll(pageable)).thenReturn(page);

        var result = service.getList(1, 13);

        assertEquals(113, result.getData().get(0).getValue());
        assertEquals(2, result.getData().size() + 1);
    }

    @Test
    void save_unitTest() {
        TurnTimeDurationConfiguration turnTimeDurationConfiguration = TurnTimeDurationConfiguration.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 minute")
                .build();

        TurnTimeDurationConfigurationDTO turnTimeDurationConfigurationDTO = TurnTimeDurationConfigurationDTO.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 minute")
                .build();

        when(turnTimeDurationConfigurationRepository.save(turnTimeDurationConfiguration)).thenReturn(turnTimeDurationConfiguration);
        when(turnTimeDurationConfigurationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(Optional.of(turnTimeDurationConfiguration));
        var result = service.save(turnTimeDurationConfigurationDTO);

        assertEquals("OK", result.getCode());
        assertEquals("Success updated turn time configuration data", result.getMessage());
    }

    @Test
    void getDetail_unitTest() {
        TurnTimeDurationConfiguration turnTimeDurationConfiguration = TurnTimeDurationConfiguration.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .value(113)
                .label("113 minute")
                .build();

        when(turnTimeDurationConfigurationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(Optional.of(turnTimeDurationConfiguration));

        var result = service.getDetail(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        assertEquals(113, result.getValue());
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result.getId());
    }
}