package ru.school21.TicTacToe.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import ru.school21.TicTacToe.datasource.InMemoryGameRepo;
import ru.school21.TicTacToe.domain.model.Field;
import ru.school21.TicTacToe.domain.model.Game;
import ru.school21.TicTacToe.enums.GameStateEnum;
import ru.school21.TicTacToe.web.mapper.GameMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService implements GameEngine {

    private final InMemoryGameRepo inMemoryGameRepo;
    private final FieldService fieldService;
    private final GameMapper gameMapper;

    @Override
    public Game createNewGame() {
        Game game = new Game(UUID.randomUUID(), fieldService.serializeField(new Field()));
        game.setStatus(GameStateEnum.getInProgress());
        saveGame(game);
        return game;
    }

    @Override
    public void saveGame(Game game) {
        inMemoryGameRepo.save(game);
    }

    @Override
    public Game getGame(UUID gameId) {
        return inMemoryGameRepo.findById(gameId)
                .orElseThrow(() -> new ExpressionException("Game not found: " + gameId));
    }

    @Override
    public void makeMove(Game game){
        synchronized (game.getId().toString().intern()) {
            Field currentField = fieldService.deserializeField(game.getFieldState());
            int gameResult = getGameState(currentField);
            if (gameResult != 0) {
                if (gameResult == 1) game.setStatus(GameStateEnum.WIN.getDescription());
                else if (gameResult == -1) game.setStatus(GameStateEnum.LOSE.getDescription());
                else if (gameResult == 2) game.setStatus(GameStateEnum.NOBODY.getDescription());
                return;
            }
            int[] computerMove = minimax(currentField);
            if (computerMove != null) {
                currentField.setElement(computerMove[0], computerMove[1], -1);
                game.setFieldState(fieldService.serializeField(currentField));

                gameResult = getGameState(currentField);
                if (gameResult == 1) {
                    game.setStatus(GameStateEnum.WIN.getDescription());
                } else if (gameResult == -1) {
                    game.setStatus(GameStateEnum.LOSE.getDescription());
                } else if (gameResult == 2) {
                    game.setStatus(GameStateEnum.NOBODY.getDescription());
                }
            }
        }
    }

    @Override
    public void makeMoveWithFullBoard(UUID gameId, int[][] clientBoard) throws Exception {
        synchronized (gameId.toString().intern()) {
            Game game = getGame(gameId);

            if (!GameStateEnum.getInProgress().equals(game.getStatus())) {
                throw new Exception(GameStateEnum.getGameOver() + game.getStatus());
            }

            int[][] currentBoard = gameMapper.deserializeBoard(game.getFieldState());

            if (!isValidBoard(clientBoard)) {
                throw new IllegalArgumentException("Некорректный формат игрового поля");
            }

            if (!isValidTransition(currentBoard, clientBoard)) {
                throw new IllegalArgumentException("Некорректный ход: поле изменено неправильно");
            }

            game.setFieldState(gameMapper.serializeBoard(clientBoard));
            makeMove(game);
            saveGame(game);
            gameMapper.toResponse(game);
        }
    }

    private boolean isValidBoard(int[][] board) {
        if (board == null || board.length != 3) return false;
        for (int i = 0; i < 3; i++) {
            if (board[i] == null || board[i].length != 3) return false;
            for (int j = 0; j < 3; j++) {
                int val = board[i][j];
                if (val != -1 && val != 0 && val != 1) return false;
            }
        }
        return true;
    }

    private boolean isValidTransition(int[][] before, int[][] after) {
        int changes = 0;
        int row = -1, col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (before[i][j] != after[i][j]) {
                    changes++;
                    row = i;
                    col = j;
                }
            }
        }
        return changes == 1 && before[row][col] == 0 && after[row][col] == 1;
    }

    public Field copyField(Field original) {
        Field copy = new Field();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy.setElement(i, j, original.getElement(i, j));
            }
        }
        return copy;
    }

    @Override
    public int[] minimax(Field originalField) {
        Field field = copyField(originalField);
        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field.getElement(i, j) == 0) {
                    field.setElement(i, j, -1);
                    int score = minimax(field, 0, false);
                    field.setElement(i, j, 0);

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    public int minimax(Field field, int depth, boolean isMaximizing) {
        int result = getGameState(field);
        if (result != 0) {
            return evaluateScore(result, depth);
        }

        if (depth >= 5) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field.getElement(i, j) == 0) {
                        field.setElement(i, j, -1);
                        int score = minimax(field, depth + 1, false);
                        field.setElement(i, j, 0);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field.getElement(i, j) == 0) {
                        field.setElement(i, j, 1);
                        int score = minimax(field, depth + 1, true);
                        field.setElement(i, j, 0);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public int evaluateScore(int gameResult, int depth) {
        if (gameResult == -1) {
            return 10 - depth;
        } else if (gameResult == 1) {
            return depth - 10;
        } else {
            return 0;
        }
    }

    @Override
    public int getGameState(Field field) {
        for (int i = 0; i < 3; i++) {
            if (field.getElement(i, 0) != 0 &&
                    field.getElement(i, 0) == field.getElement(i, 1) &&
                    field.getElement(i, 1) == field.getElement(i, 2)) {
                return field.getElement(i, 0);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field.getElement(0, i) != 0 &&
                    field.getElement(0, i) == field.getElement(1, i) &&
                    field.getElement(1, i) == field.getElement(2, i)) {
                return field.getElement(0, i);
            }
        }

        if (field.getElement(0, 0) != 0 &&
                field.getElement(0, 0) == field.getElement(1, 1) &&
                field.getElement(1, 1) == field.getElement(2, 2)) {
            return field.getElement(0, 0);
        }

        if (field.getElement(0, 2) != 0 &&
                field.getElement(0, 2) == field.getElement(1, 1) &&
                field.getElement(1, 1) == field.getElement(2, 0)) {
            return field.getElement(0, 2);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field.getElement(i, j) == 0) {
                    return 0;
                }
            }
        }
        return 2;
    }
}
