package ru.school21.TicTacToe.datasource.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameData {
    private UUID id;
    private String status;
    private String fieldState;
}
