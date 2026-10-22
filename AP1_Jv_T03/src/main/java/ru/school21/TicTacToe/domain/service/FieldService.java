package ru.school21.TicTacToe.domain.service;

import org.springframework.stereotype.Service;
import ru.school21.TicTacToe.domain.model.Field;

@Service
public class FieldService {

    public Field deserializeField(String fieldState) {
        Field field = new Field();
        if (fieldState != null && fieldState.length() == 9) {
            char[] chars = fieldState.toCharArray();
            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int col = i % 3;
                char c = chars[i];
                if (c == 'X') field.setElement(row, col, 1);
                else if (c == 'O') field.setElement(row, col, -1);
                else field.setElement(row, col, 0);
            }
        }
        return field;
    }

    public String serializeField(Field field) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = field.getElement(i, j);
                if (value == 1) sb.append('X');
                else if (value == -1) sb.append('O');
                else sb.append('_');
            }
        }
        return sb.toString();
    }
}
