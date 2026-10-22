package ru.school21.TicTacToe.datasource;

import ru.school21.TicTacToe.domain.model.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepo {
    void save(Game game);
    Optional<Game> findById(UUID id);
}
