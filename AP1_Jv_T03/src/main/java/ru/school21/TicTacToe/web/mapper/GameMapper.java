package ru.school21.TicTacToe.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.school21.TicTacToe.domain.model.Game;
import ru.school21.TicTacToe.web.model.GameResponse;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "id", target = "gameId")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "board", expression = "java(deserializeBoard(game.getFieldState()))")
    GameResponse toResponse(Game game);

    default int[][] deserializeBoard(String fieldState) {
        if (fieldState == null) return new int[3][3];
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            char c = fieldState.charAt(i);
            int row = i / 3, col = i % 3;
            board[row][col] = c == 'X' ? 1 : c == 'O' ? -1 : 0;
        }
        return board;
    }

    default String serializeBoard(int[][] board) {
        if (board == null) return "_________";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = board[i][j];
                sb.append(val == 1 ? 'X' : val == -1 ? 'O' : '_');
            }
        }
        return sb.toString();
    }
}