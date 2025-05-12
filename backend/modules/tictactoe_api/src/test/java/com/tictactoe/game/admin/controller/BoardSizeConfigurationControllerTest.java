package com.tictactoe.game.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tictactoe.game.dto.BoardSizeConfigurationDTO;
import com.tictactoe.game.dto.BoardSizeConfigurationsDTO;
import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.service.BoardSizeConfigurationService;
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
class BoardSizeConfigurationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private BoardSizeConfigurationService boardSizeConfigurationService;

    @InjectMocks
    private BoardSizeConfigurationController boardSizeConfigurationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(boardSizeConfigurationController).build();
    }

    @Test
    void testGetList_Success() throws Exception {
        BoardSizeConfigurationsDTO responseDTO = new BoardSizeConfigurationsDTO();
        responseDTO.setData(Arrays.asList(BoardSizeConfigurationDTO.builder().value(113).build()));
        Mockito.when(boardSizeConfigurationService.getList(1, 13)).thenReturn(
                responseDTO
        );

        mvc.perform(get("/admin/board_size_configuration"))
                .andExpect(status().isOk());
    }

    @Test
    void testSave_Success() throws Exception {
        BoardSizeConfigurationDTO dto = BoardSizeConfigurationDTO.builder().value(113).build();
        Mockito.when(boardSizeConfigurationService.save(dto)).thenReturn(
                StatusDetailDTO.builder().build()
        );

        mvc.perform(post("/admin/board_size_configuration").content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDetail_Success() throws Exception {
        Mockito.when(boardSizeConfigurationService.getDetail(UUID.fromString("00000000-0000-0000-0000-000000000000"))).thenReturn(
                BoardSizeConfigurationDTO.builder().value(113).build()
        );

        mvc.perform(get("/admin/board_size_configuration/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isOk());
    }

}
