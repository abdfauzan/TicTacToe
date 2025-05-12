package com.tictactoe.game.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationsDTO;
import com.tictactoe.game.service.TurnTimeDurationConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TurnTimeDurationConfigurationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private TurnTimeDurationConfigurationService turnTimeDurationConfigurationService;

    @InjectMocks
    private TurnTimeDurationConfigurationController turnTimeDurationConfigurationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(turnTimeDurationConfigurationController).build();
    }

    @Test
    void testGetList_Success() throws Exception {
        TurnTimeDurationConfigurationsDTO responseDTO = new TurnTimeDurationConfigurationsDTO();
        responseDTO.setData(Arrays.asList(TurnTimeDurationConfigurationDTO.builder().value(113).build()));
        Mockito.when(turnTimeDurationConfigurationService.getList(1, 13)).thenReturn(
                responseDTO
        );

        mvc.perform(get("/admin/turn_time_duration_configuration"))
                .andExpect(status().isOk());
    }

    @Test
    void testSave_Success() throws Exception {
        TurnTimeDurationConfigurationDTO dto = TurnTimeDurationConfigurationDTO.builder().value(113).build();
        Mockito.when(turnTimeDurationConfigurationService.save(dto)).thenReturn(
                StatusDetailDTO.builder().build()
        );

        mvc.perform(post("/admin/turn_time_duration_configuration").content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDetail_Success() throws Exception {
        Mockito.when(turnTimeDurationConfigurationService.getDetail(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(
                TurnTimeDurationConfigurationDTO.builder().value(113).build()
        );

        mvc.perform(get("/admin/turn_time_duration_configuration/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isOk());
    }

}
