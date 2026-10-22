package ru.school21.TicTacToe.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GameResponse {
    private int[][] board;
    private UUID gameId;
    private String status;
}
