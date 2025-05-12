package com.tictactoe.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(basePackages = {"com.tictactoe.game"})
@EnableJpaRepositories({"com.tictactoe.game.repository"})
@EnableAsync
@SpringBootApplication
@EntityScan(basePackages = {"com.tictactoe.game.model"})
public class TicTacToeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApiApplication.class, args);
    }
}
