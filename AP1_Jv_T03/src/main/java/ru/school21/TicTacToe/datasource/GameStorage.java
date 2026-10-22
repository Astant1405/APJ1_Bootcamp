package ru.school21.TicTacToe.datasource;

import org.springframework.stereotype.Component;
import ru.school21.TicTacToe.datasource.model.GameData;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameStorage {
    private final Map<UUID, GameData> games = new ConcurrentHashMap<>();

    public void save(UUID id, GameData gameData) { games.put(id, gameData); }
    public Optional<GameData> findById(UUID id) { return Optional.ofNullable(games.get(id)); }
}
