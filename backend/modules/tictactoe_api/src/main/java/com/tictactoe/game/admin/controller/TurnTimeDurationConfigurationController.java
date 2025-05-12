package com.tictactoe.game.admin.controller;

import com.tictactoe.game.dto.StatusDetailDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationDTO;
import com.tictactoe.game.dto.TurnTimeDurationConfigurationsDTO;
import com.tictactoe.game.service.TurnTimeDurationConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@CustomLog
@RestController
@RequestMapping(value = "/admin")//, headers = "x-api-version=v02")
public class TurnTimeDurationConfigurationController {

    private final TurnTimeDurationConfigurationService turnTimeDurationConfigurationService;

    public TurnTimeDurationConfigurationController(TurnTimeDurationConfigurationService turnTimeDurationConfigurationService) {
        this.turnTimeDurationConfigurationService = turnTimeDurationConfigurationService;
    }

    @GetMapping("/turn_time_duration_configuration")
    public ResponseEntity<TurnTimeDurationConfigurationsDTO> getList(@RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false) Integer pageSize) {
        TurnTimeDurationConfigurationsDTO turnTimeDurationConfigurationsDTO = new TurnTimeDurationConfigurationsDTO();
        if (page == null || pageSize == null) {
            turnTimeDurationConfigurationsDTO = turnTimeDurationConfigurationService.getList(1, 13);
        } else {
            turnTimeDurationConfigurationsDTO = turnTimeDurationConfigurationService.getList(page, pageSize);
        }
        return ResponseEntity.ok().body(turnTimeDurationConfigurationsDTO);
    }

    @PostMapping("/turn_time_duration_configuration")
    public ResponseEntity<StatusDetailDTO> save(@RequestBody TurnTimeDurationConfigurationDTO turnTimeDurationConfigurationDTO) {
        return ResponseEntity.ok().body(turnTimeDurationConfigurationService.save(turnTimeDurationConfigurationDTO));
    }

    @GetMapping("/turn_time_duration_configuration/{id}")
    public ResponseEntity<TurnTimeDurationConfigurationDTO> getDetails(@PathVariable UUID id) {
        return ResponseEntity.ok().body(turnTimeDurationConfigurationService.getDetail(id));
    }
}
