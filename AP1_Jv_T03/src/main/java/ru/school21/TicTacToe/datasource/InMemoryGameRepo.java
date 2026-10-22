package ru.school21.TicTacToe.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.school21.TicTacToe.datasource.mapper.GameDataMapper;
import ru.school21.TicTacToe.datasource.model.GameData;
import ru.school21.TicTacToe.domain.model.Game;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InMemoryGameRepo implements GameRepo {
    private final GameStorage storage;
    private final GameDataMapper mapper;

    @Override
    public void save(Game game) {
        GameData data = mapper.toGameData(game);
        storage.save(game.getId(), data);
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return storage.findById(id).map(mapper::toGame);
    }

}
