package ru.school21.TicTacToe.enums;

import lombok.Getter;

@Getter
public enum GameStateEnum {
    WIN(1, "Победа"),
    LOSE(-1, "Поражение"),
    NOBODY(2, "Ничья");

    private final int code;
    private final String description;
    private static final String IN_PROGRESS = "В процессе";
    private static final String GAME_OVER = "Игра уже завершена: ";

    GameStateEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getInProgress() {
        return IN_PROGRESS;
    }

    public static String getGameOver() {
        return GAME_OVER;
    }

}
