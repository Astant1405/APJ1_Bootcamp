package ru.school21.TicTacToe.web.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.school21.TicTacToe.domain.model.Game;
import ru.school21.TicTacToe.web.model.GameResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-19T17:56:07+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.17 (Ubuntu)"
)
@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public GameResponse toResponse(Game game) {
        if ( game == null ) {
            return null;
        }

        GameResponse gameResponse = new GameResponse();

        gameResponse.setGameId( game.getId() );
        gameResponse.setStatus( game.getStatus() );

        gameResponse.setBoard( deserializeBoard(game.getFieldState()) );

        return gameResponse;
    }
}
