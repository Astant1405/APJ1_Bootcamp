package ru.school21.TicTacToe.domain.service;

import ru.school21.TicTacToe.domain.model.Field;
import ru.school21.TicTacToe.domain.model.Game;

import java.util.UUID;

public interface GameEngine{
    int getGameState(Field field);
    int[] minimax(Field originalField);
    void makeMoveWithFullBoard(UUID gameId, int[][] clientBoard) throws Exception;
    void makeMove(Game game);
    void saveGame(Game game);
    Game createNewGame();
    Game getGame(UUID gameId);
}
