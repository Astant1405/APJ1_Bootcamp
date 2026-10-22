package ru.school21.TicTacToe.domain.model;


import lombok.Getter;

@Getter
public class Field {
    private final int[][] field;

    public Field() {
        this.field = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                field[i][j] = 0;
            }
        }
    }

    public int getElement(int n, int m) {
        return field[n][m];
    }

    public void setElement(int n, int m, int value) {
        field[n][m] = value;
    }

}
