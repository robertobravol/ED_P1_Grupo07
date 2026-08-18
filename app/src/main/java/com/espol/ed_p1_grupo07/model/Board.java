package com.espol.ed_p1_grupo07.model;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[][] cells;

    public static final int CIRCLE = -1;
    public static final int X = 1;
    public static final int EMPTY_CELL = 0;

    // Tablero 3x3
    public Board(){
        this.cells = new int[][] {
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL}
        };
    }

    // Limpia el tablero actual.
    public void cleanBoard() {
        this.cells = new int[][] {
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL},
                {EMPTY_CELL, EMPTY_CELL, EMPTY_CELL}
        };
    }

    // Obtiene el valor de una celda.
    public int getCell(int row, int column) {
        if (!isValidPosition(row, column)) {
            throw new IllegalArgumentException("Posición fuera del tablero.");
        }
        return cells[row][column];
    }

    // Retorna una lista con las posiciones de las celdas vacías.
    public List<int[]> getEmptyCells(){

        List<int[]> list = new LinkedList<>();

        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                if(cells[i][j] == EMPTY_CELL){
                    list.add(new int[]{i, j});
                }
            }
        }

        return list;
    }

    // Verifica si una posicion (row, column) es válida.
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < cells.length && column >= 0 && column < cells[row].length;
    }

    // Verifica cuál jugador es el ganador.
    public int checkWinner(){
        // Verifica filas
        for (int i = 0; i <= 2; i++) {
            if (cells[i][0] != EMPTY_CELL && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2]) {
                return cells[i][0];
            }
        }

        // Verifica columnas
        for (int j = 0; j <= 2; j++) {
            if (cells[0][j] != EMPTY_CELL && cells[0][j] == cells[1][j] && cells[1][j] == cells[2][j]) {
                return cells[0][j];
            }
        }

        // Verifica diagonales
        if (cells[0][0] != EMPTY_CELL && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]) {
            return cells[0][0];
        }

        if (cells[0][2] != EMPTY_CELL && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]) {
            return cells[0][2];
        }

        // 0: No hay ganador
        return 0;
    }

    // Comprueba si no hay casillas vacías o todas las casillas están ocupadas.
    public boolean isFull(){
        return getEmptyCells().isEmpty();
    }

    // Marca una casilla en el tablero con el símbolo del jugador.
    public boolean markCell(int row, int column, int playerSymbol){
        if (!isValidPosition(row, column)) {
            throw new IllegalArgumentException("Posición fuera del tablero.");
        }

        // Verifica si a celda esta libre para marcar
        if (this.cells[row][column] == EMPTY_CELL) {
            this.cells[row][column] = playerSymbol;
            return true;
        }
        return false;
    }

    // Crea una copia del tablero actual y lo retorna.
    public Board cloneBoard() {
        Board newBoard = new Board();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int value = this.cells[i][j];
                newBoard.cells[i][j] = value;
            }
        }

        return newBoard;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board other = (Board) obj;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (this.cells[i][j] != other.cells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                result = 31 * result + cells[i][j];
            }
        }
        return result;
    }
}
