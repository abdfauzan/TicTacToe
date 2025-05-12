package com.tictactoe.game.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tictactoe.game.dto.GameConfigurationDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationsDTO;
import com.tictactoe.game.service.GameConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GameConfigurationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private GameConfigurationService gameConfigurationService;

    @InjectMocks
    private GameConfigurationController gameConfigurationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(gameConfigurationController).build();
    }

    @Test
    void testGetConfiguration_Success() throws Exception {
        TurnTimeDurationConfigurationsDTO responseDTO = new TurnTimeDurationConfigurationsDTO();
        GameConfigurationDTO gameConfigurationDTO = new GameConfigurationDTO();
        Mockito.when(gameConfigurationService.getConfiguration()).thenReturn(
                gameConfigurationDTO
        );

        mvc.perform(get("/client/configuration"))
                .andExpect(status().isOk());
    }
}
