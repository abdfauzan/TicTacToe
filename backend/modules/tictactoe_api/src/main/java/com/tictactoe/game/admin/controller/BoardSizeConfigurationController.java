package com.tictactoe.game.admin.controller;

import com.tictactoe.game.dto.BoardSizeConfigurationDTO;
import com.tictactoe.game.dto.BoardSizeConfigurationsDTO;
import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.service.BoardSizeConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@CustomLog
@RestController
@RequestMapping(value = "/admin")//, headers = "x-api-version=v02")
public class BoardSizeConfigurationController {

    private final BoardSizeConfigurationService boardSizeConfigurationService;

    public BoardSizeConfigurationController(BoardSizeConfigurationService boardSizeConfigurationService) {
        this.boardSizeConfigurationService = boardSizeConfigurationService;
    }

    @GetMapping("/board_size_configuration")
    public ResponseEntity<BoardSizeConfigurationsDTO> getBoardSize(@RequestParam(required = false) Integer page,
                                                                           @RequestParam(required = false) Integer pageSize) {
        BoardSizeConfigurationsDTO boardSizeConfigurationsDTO = new BoardSizeConfigurationsDTO();
        if (page == null || pageSize == null) {
            boardSizeConfigurationsDTO = boardSizeConfigurationService.getList(1, 13);
        } else {
            boardSizeConfigurationsDTO = boardSizeConfigurationService.getList(page, pageSize);
        }
        return ResponseEntity.ok().body(boardSizeConfigurationsDTO);
    }

    @PostMapping("/board_size_configuration")
    public ResponseEntity<StatusDetailDTO> saveBoardSize(@RequestBody BoardSizeConfigurationDTO boardSizeConfigurationDTO) {
        return ResponseEntity.ok().body(boardSizeConfigurationService.save(boardSizeConfigurationDTO));
    }

    @GetMapping("/board_size_configuration/{id}")
    public ResponseEntity<BoardSizeConfigurationDTO> getDetails(@PathVariable UUID id) {
        return ResponseEntity.ok().body(boardSizeConfigurationService.getDetail(id));
    }
}
