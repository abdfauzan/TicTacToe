package com.tictactoe.game.client.controller;

import com.tictactoe.game.dto.GameConfigurationDTO;
import com.tictactoe.game.service.GameConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CustomLog
@RestController
@RequestMapping(value = "/client")//, headers = "x-api-version=v02")
public class GameConfigurationController {

    private final GameConfigurationService gameConfigurationService;

    public GameConfigurationController(GameConfigurationService gameConfigurationService) {
        this.gameConfigurationService = gameConfigurationService;
    }

    @GetMapping("/configuration")
    public ResponseEntity<GameConfigurationDTO> getGameConfiguration() {
        return ResponseEntity.ok().body(gameConfigurationService.getConfiguration());
    }
}
