package ru.school21.TicTacToe.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.school21.TicTacToe.datasource.GameRepo;
import ru.school21.TicTacToe.datasource.GameStorage;
import ru.school21.TicTacToe.datasource.InMemoryGameRepo;
import ru.school21.TicTacToe.datasource.mapper.GameDataMapper;
import ru.school21.TicTacToe.domain.service.FieldService;
import ru.school21.TicTacToe.domain.service.GameEngine;
import ru.school21.TicTacToe.domain.service.GameService;
import ru.school21.TicTacToe.web.mapper.GameMapper;

@Configuration
public class AppConfig {
    @Bean
    public GameStorage gameStorage() {
        return new GameStorage();
    }
    @Bean
    public GameRepo gameRepository(GameStorage storage, GameDataMapper mapper) {
        return new InMemoryGameRepo(storage, mapper);
    }
    @Bean
    public GameEngine ticTacGameService(InMemoryGameRepo inMemoryGameRepo, FieldService fieldService, GameMapper gameMapper) {
        return new GameService(inMemoryGameRepo, fieldService, gameMapper);
    }
}