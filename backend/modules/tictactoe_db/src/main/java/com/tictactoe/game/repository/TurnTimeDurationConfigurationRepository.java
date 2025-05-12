package com.tictactoe.game.repository;

import com.tictactoe.game.model.TurnTimeDurationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TurnTimeDurationConfigurationRepository extends JpaRepository<TurnTimeDurationConfiguration, UUID> {
}