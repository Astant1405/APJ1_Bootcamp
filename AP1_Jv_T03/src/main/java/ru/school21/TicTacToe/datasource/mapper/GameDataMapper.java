package ru.school21.TicTacToe.datasource.mapper;

import org.mapstruct.Mapper;
import ru.school21.TicTacToe.datasource.model.GameData;
import ru.school21.TicTacToe.domain.model.Game;

@Mapper(componentModel = "spring")
public interface GameDataMapper {
    GameData toGameData(Game game);
    Game toGame(GameData game);
}
