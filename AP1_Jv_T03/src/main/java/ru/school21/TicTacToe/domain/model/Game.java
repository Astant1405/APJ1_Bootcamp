package ru.school21.TicTacToe.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Game {

    private UUID id;
    private String status;
    private String fieldState;

    public Game(UUID id, String fieldState) {
        this.id = id;
        this.fieldState = fieldState;
    }
}
