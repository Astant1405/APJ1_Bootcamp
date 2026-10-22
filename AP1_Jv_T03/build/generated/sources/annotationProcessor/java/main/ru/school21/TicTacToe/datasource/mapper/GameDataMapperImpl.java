package ru.school21.TicTacToe.datasource.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.school21.TicTacToe.datasource.model.GameData;
import ru.school21.TicTacToe.domain.model.Game;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-19T17:56:07+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.17 (Ubuntu)"
)
@Component
public class GameDataMapperImpl implements GameDataMapper {

    @Override
    public GameData toGameData(Game game) {
        if ( game == null ) {
            return null;
        }

        GameData gameData = new GameData();

        gameData.setId( game.getId() );
        gameData.setStatus( game.getStatus() );
        gameData.setFieldState( game.getFieldState() );

        return gameData;
    }

    @Override
    public Game toGame(GameData game) {
        if ( game == null ) {
            return null;
        }

        Game game1 = new Game();

        game1.setId( game.getId() );
        game1.setStatus( game.getStatus() );
        game1.setFieldState( game.getFieldState() );

        return game1;
    }
}
