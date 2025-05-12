package com.tictactoe.game.repository;

import com.tictactoe.game.model.AIDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIDifficultyRepository extends JpaRepository<AIDifficulty, UUID> {
}